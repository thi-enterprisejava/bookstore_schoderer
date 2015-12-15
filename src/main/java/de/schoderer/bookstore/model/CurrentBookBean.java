package de.schoderer.bookstore.model;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;
import de.schoderer.bookstore.domain.DataFileLocation;
import de.schoderer.bookstore.domain.Tag;
import de.schoderer.bookstore.utils.Pages;
import org.apache.log4j.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by schod on 07.11.2015.
 */
@Named
@ViewScoped
public class CurrentBookBean implements Serializable {
    private static final Logger LOG = Logger.getLogger(CurrentBookBean.class);
    //TODO ändern!!
    private static final Path paths = Paths.get(System.getProperty("user.home"), "files");
    private static final Random random = new Random();

    private ActivePageBean pageSwitcher;
    private BookPersistence persistence;

    private Book currentBook;


    private String tag;
    private Long id;
    private Part imageFile;
    private Part bookFile;


    @Inject
    public CurrentBookBean(BookPersistence persistence, ActivePageBean pageSwitcher) throws IOException {
        this.persistence = persistence;
        this.pageSwitcher = pageSwitcher;
        currentBook = new Book();
    }

    public void doSetCurrentBook() {
        if (id == null) {
            currentBook = new Book();
        } else {
            currentBook = persistence.fetchBookByID(id);
            id = null;
        }
    }


    public void doAddTags() {
        doAddTags(tag);
        tag = "";
    }

    public void doAddTags(String string) {
        if (string != null && !"".equals(string)) {
            Stream.of(string.split(",")).forEach(tag -> currentBook.getTags().add(new Tag(tag.trim())));
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Added Tag: " + tag + " - Current list size: " + currentBook.getTags().size());
        }
    }

    public void removeTag(String tag) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Removed Tag: " + tag);
        }
        currentBook.getTags().removeIf(bookTag -> bookTag.getTag().equals(tag));
    }

    /**
     * Saves the book to the db and uploades the files to the server
     */
    public void doSave() {

        if (id == null) {
            currentBook.setData(uploadAndSaveFiles());
            saveTags(currentBook);
            persistence.saveBook(currentBook);
        } else {
            currentBook.setData(uploadAndSaveFiles());
            saveTags(currentBook);
            persistence.updateBook(currentBook);
        }
        currentBook = new Book();
    }

    /**
     * Exchanges the Tags of the Book with the Tags in the Database (if exists)
     * Made to make sure a Tag is unique in the Database
     *
     * @param currentBook
     */
    private void saveTags(Book currentBook) {
        List<Tag> tagsInDatabase = persistence.fetchAllTags();
        List<Tag> tagsWithID = new ArrayList<>(currentBook.getTags().size());
        currentBook.getTags().forEach(tag -> {
            Tag databaseTag;
            if (tagsInDatabase.contains(tag)) {
                databaseTag = tagsInDatabase.get(tagsInDatabase.indexOf(tag));
            } else {
                databaseTag = persistence.saveTag(tag);
            }
            tagsWithID.add(databaseTag);
        });

        currentBook.setTags(tagsWithID);
    }


    private DataFileLocation uploadAndSaveFiles() {
        //TODO mabye onlay upload on update, when change happend
        if (currentBook.getId() != null) {
            return currentBook.getData();
        }
        DataFileLocation location;
        location = new DataFileLocation();
        uploadAndSaveBookLocation(location);
        uploadAndSaveImageLocation(location);
        return location;
    }

    private void uploadAndSaveImageLocation(DataFileLocation location) {
        Path fullImagePath = uploadAndSaveFileToHardDisk(imageFile, false);
        if (fullImagePath != null) {
            location.setImageName(fullImagePath.getFileName().toString());
            location.setFullImagePath(fullImagePath.toAbsolutePath().toString());
        }
    }

    private void uploadAndSaveBookLocation(DataFileLocation location) {
        Path fullBookPath = uploadAndSaveFileToHardDisk(bookFile, true);
        if (fullBookPath != null) {
            location.setFileName(fullBookPath.getFileName().toString());
            location.setFullFilePath(fullBookPath.toAbsolutePath().toString());
        }
    }


    private Path uploadAndSaveFileToHardDisk(Part part, boolean isBook) {
        Path filePath = null;
        if (part == null) {
            return filePath;
        }
        try {

            String fileName = part.getSubmittedFileName();
            filePath = getBasePath(isBook).resolve(createFileName(fileName));
            Files.copy(part.getInputStream(), filePath);
            if (LOG.isInfoEnabled()) {
                LOG.info("Successly saved File: " + filePath.toAbsolutePath().toString());
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return filePath;
    }

    private static Path getBasePath(boolean isBook) {
        if (isBook) {
            return paths.resolve("books");
        } else {
            return paths.resolve("images");
        }
    }

    /**
     * Create a Random Filename for the file on the server (protection from accidentilly overrride)
     *
     * @param fileName
     * @return
     */
    private String createFileName(String fileName) {
        int beginIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, beginIndex - 1) + "_" + Math.abs(random.nextLong()) + fileName.substring(beginIndex);
    }


    public String deleteBook() {
        removeFiles();
        persistence.removeBook(currentBook);
        return pageSwitcher.switchPage(Pages.INDEX);
    }

    private void removeFiles() {
        try {
            Files.deleteIfExists(Paths.get(currentBook.getData().getFullFilePath()));
            Files.deleteIfExists(Paths.get(currentBook.getData().getFullImagePath()));
        } catch (IOException e) {
            LOG.error("Couldn't delete Files of Book: " + currentBook.getTitle() + ": " + e.getMessage(), e);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(Part imageFile) {
        this.imageFile = imageFile;
    }

    public Part getBookFile() {
        return bookFile;
    }

    public void setBookFile(Part bookFile) {
        this.bookFile = bookFile;
    }

}
