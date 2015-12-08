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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    private long id = -1;
    private Part imageFile;
    private Part bookFile;


    @Inject
    public CurrentBookBean(BookPersistence persistence, ActivePageBean pageSwitcher) throws IOException {
        this.persistence = persistence;
        this.pageSwitcher = pageSwitcher;
        currentBook = new Book();
    }

    public void doSetCurrentBook() {
        if (id > 0) {
            currentBook = persistence.fetchBookByID(id);
        } else {
            LOG.info("Setting currentBook to new");
            currentBook = new Book();
        }
    }


    public void doAddTag() {
        doAddTag(tag);
        tag = "";
    }

    public void doAddTag(String string) {
        if (string != null && !"".equals(string)) {
            currentBook.getTags().add(new Tag(tag.toUpperCase()));
        }
        LOG.info("Added Tag: " + tag + " - Current list size: " + currentBook.getTags().size());
    }

    public void removeTag(String tag) {
        LOG.info("Removing Tag:" + tag + " BookTags: " + currentBook.getTags().size());
        currentBook.getTags().removeIf(bookTag -> bookTag.getTag().equals(tag));
        LOG.info("Removed!! - BookTags: " + currentBook.getTags().size());

    }

    public void doSave() {
        currentBook.setData(uploadAndSaveFiles());
        saveTags(currentBook);
        if (id < 0) {
            persistence.saveBook(currentBook);
        } else {
            persistence.updateBook(currentBook);
        }
        LOG.info("Saveing book:" + currentBook + " - With Tags: " + currentBook.getTags().size());
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
        for (Iterator<Tag> tagIterator = currentBook.getTags().iterator(); tagIterator.hasNext(); ) {
            Tag tag = tagIterator.next();
            Tag databaseTag;
            if (tagsInDatabase.contains(tag)) {
                databaseTag = tagsInDatabase.get(tagsInDatabase.indexOf(tag));
            } else {
                databaseTag = persistence.saveTag(tag);
            }
            tagsWithID.add(databaseTag);
        }
        currentBook.setTags(tagsWithID);
    }


    private DataFileLocation uploadAndSaveFiles() {
        DataFileLocation location = new DataFileLocation();
        location.setFileLocation(uploadAndSaveFileToHardDisk(bookFile));
        location.setImageLocation(uploadAndSaveFileToHardDisk(imageFile));
        return location;
    }


    private String uploadAndSaveFileToHardDisk(Part part) {
        Path filePath = null;
        try {
            String fileName = part.getSubmittedFileName();
            //CreateRandomName for file
            filePath = paths.resolve(createFileName(fileName));
            Files.copy(part.getInputStream(), filePath);
            LOG.info("Successly saved File: " + filePath.toAbsolutePath().toString());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
        return filePath.toString();
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
            Files.deleteIfExists(Paths.get(currentBook.getData().getFileLocation()));
            Files.deleteIfExists(Paths.get(currentBook.getData().getImageLocation()));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
