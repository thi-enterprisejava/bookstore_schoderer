package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.services.BookService;
import de.schoderer.bookstore.services.Configuration;
import de.schoderer.bookstore.utils.Pages;
import org.apache.log4j.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Created by schod on 07.11.2015.
 */
@Named
@ViewScoped
public class CurrentBookBean implements Serializable {
    private static final Logger LOG = Logger.getLogger(CurrentBookBean.class);
    @Inject
    private BookService bookService;
    @Inject
    private Configuration configuration;
    @Inject
    private PageSwitcherBean pageSwitcher;

    private Book currentBook;


    private String tagName;
    private Long id;
    private Part imageFile;
    private Part bookFile;


    public CurrentBookBean() {
        this(null);
    }

    public CurrentBookBean(Long id) {
        this.id = id;
    }

    public void doSetCurrentBook() {
        if (id == null || id < 0) {
            currentBook = new Book();
        } else {
            currentBook = bookService.fetchBookById(id);
            id = null;
        }
    }

    /**
     * Add Tags to the Tag list of the current Book, Checks also if tagName is not null or empty
     */
    public void doAddTags() {
        doAddTags(tagName);
        tagName = "";
    }

    /**
     * Add Tags to the Tag list of the current Book, Checks also if tagName is not null or empty
     *
     * @param string
     */
    public void doAddTags(String string) {
        if (string != null && !"".equals(string)) {
            Stream.of(string.split(","))
                    .map(String::toUpperCase)
                    .distinct()
                    .forEach(tagInList -> currentBook.getTags().add(new Tag(tagInList.trim())));
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Added Tag: " + tagName + " - Current list size: " + currentBook.getTags().size());
        }
    }

    public void removeTag(String tag) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Removed Tag: " + tag);
        }
        currentBook.getTags().removeIf(bookTag -> bookTag.getTagName().equals(tag.toUpperCase().trim()));
    }

    /**
     * Saves the book to the db and uploades the files to the server
     */
    public void doSave() {
        currentBook.setData(uploadAndSaveFiles());
        if (currentBook.getId() == null) {
            bookService.saveBook(currentBook);
        } else {
            bookService.updateBook(currentBook);
        }
        currentBook = new Book();
    }


    /**
     * Uploads all files to the hdd and saves the path of the files to an @DataFileLocation-Object
     *
     * @return DataFileLocation
     */
    protected DataFileLocation uploadAndSaveFiles() {
        DataFileLocation location;
        location = new DataFileLocation();
        uploadAndSaveBookLocation(location);
        uploadAndSaveImageLocation(location);
        return location;
    }

    private void uploadAndSaveImageLocation(DataFileLocation location) {
        Path fullImagePath = bookService.uploadFile(imageFile, false);
        if (fullImagePath != null) {
            location.setImageName(fullImagePath.getFileName().toString());
            location.setFullImagePath(fullImagePath.toAbsolutePath().toString());
        }
    }

    private void uploadAndSaveBookLocation(DataFileLocation location) {
        Path fullBookPath = bookService.uploadFile(bookFile, true);
        if (fullBookPath != null) {
            location.setFileName(fullBookPath.getFileName().toString());
            location.setFullFilePath(fullBookPath.toAbsolutePath().toString());
        }
    }

    /**
     * Deletes the currently selected book
     *
     * @return address of index page
     */
    public String deleteBook() {
        bookService.removeBook(currentBook);
        return pageSwitcher.switchPage(Pages.INDEX);
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public PageSwitcherBean getPageSwitcher() {
        return pageSwitcher;
    }

    public void setPageSwitcher(PageSwitcherBean pageSwitcher) {
        this.pageSwitcher = pageSwitcher;
    }
}
