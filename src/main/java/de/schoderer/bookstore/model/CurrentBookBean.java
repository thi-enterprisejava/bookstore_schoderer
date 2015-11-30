package de.schoderer.bookstore.model;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;
import de.schoderer.bookstore.domain.Tag;
import de.schoderer.bookstore.utils.Pages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by schod on 07.11.2015.
 */
@Named
@RequestScoped
public class CurrentBookBean implements Serializable {

    private static final Path paths = Paths.get(System.getProperty("user.home"), "files");
    @Inject
    private ActivePageBean pageSwitcher;
    private BookPersistence persistence;

    private long id = -1;
    private String tag;
    private Book currentBook;
    private UploadedFile imageFile;
    private UploadedFile bookFile;

    @Inject
    public CurrentBookBean(BookPersistence persistence) throws IOException {
        if (!Files.isDirectory(paths)) {
            Files.createDirectories(paths);
        }
        this.persistence = persistence;
        currentBook = new Book();
    }

    public void doSetCurrentBook() {
        if (id > 0) {
            currentBook = persistence.fetchBookByID(id);
        } else {
            currentBook = new Book();
        }
    }


    public void doAddTag() {
        if (!"".equals(tag)) {
            currentBook.getTags().add(new Tag(tag));
            tag = "";
        }
    }
/*
    public void handleImageUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public void handleBookUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    */

    public String doSave() {
        Optional<Path> filePath = uploadFiles(imageFile);
        filePath.ifPresent(path -> currentBook.getData().setImageLocation(path.toAbsolutePath().toString()));
        filePath = uploadFiles(bookFile);
        filePath.ifPresent(path -> currentBook.getData().setFileLocation(path.toAbsolutePath().toString()));
        currentBook.getTags().add(new Tag("TestTag"));
        persistence.saveBook(currentBook);
        currentBook = new Book();
        return pageSwitcher.switchPage(Pages.INDEX);
    }

    private Optional<Path> uploadFiles(UploadedFile file) {
        Optional<Path> filePath = Optional.empty();
        if (file != null) {
            try {
                Path path = paths.resolve(file.getFileName());
                Files.write(path, file.getContents());
                filePath = Optional.of(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    public void handleFileUpload(FileUploadEvent event) {
            this.imageFile = event.getFile();
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

    public UploadedFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(UploadedFile imageFile) {
        this.imageFile = imageFile;
    }

    public UploadedFile getBookFile() {
        return bookFile;
    }

    public void setBookFile(UploadedFile bookFile) {
        this.bookFile = bookFile;
    }
}
