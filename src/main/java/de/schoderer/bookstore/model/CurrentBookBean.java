package de.schoderer.bookstore.model;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;
import de.schoderer.bookstore.domain.Tag;
import de.schoderer.bookstore.utils.Pages;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
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
    private static final Logger LOG = Logger.getLogger(CurrentBookBean.class);

    private static final Path paths = Paths.get(System.getProperty("user.home"), "files");
    @Inject
    private ActivePageBean pageSwitcher;
    private BookPersistence persistence;

    private boolean hasErrors= false;
    private long id = -1;
    private String tag;
    private Book currentBook;

    private Part imageFile;
    private Part bookFile;


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
            LOG.info("Setting currentBook to new");
            currentBook = new Book();
        }
    }


    public void doAddTag() {
        if (tag!= null && !tag.isEmpty()) {
            currentBook.getTags().add(new Tag(tag));
            LOG.info("Added Tag: "+tag);
            tag = "";
        }
    }
    public void doAddTag(String string) {
        if (!"".equals(string)) {
            currentBook.getTags().add(new Tag(tag));
            LOG.info("Added Tag: "+tag);
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

    public void doSave() {
        hasErrors = false;
        LOG.info("Saveing book:" + currentBook +" - With Tags: "+ currentBook.getTags().size());
        writeFileToHardDisk(bookFile);
        persistence.saveBook(currentBook);
        currentBook = new Book();
        //return pageSwitcher.switchPage(Pages.INDEX);
    }

    private Optional<Path> writeFileToHardDisk(Part part){
        LOG.info(part.getName());
        Optional<Path> filePath = Optional.empty();
        try {
            Path path = Paths.get(System.getProperty("user.home")).resolve("test.pdf");
            Files.copy(part.getInputStream(), path);
            filePath = Optional.of(path);
            LOG.info("Success: "+path.toAbsolutePath().toString());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return filePath;
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

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }
}
