package de.schoderer.bookstore.services;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.domain.book.Tag;
import org.apache.log4j.Logger;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael Schoderer on 20.01.2016.
 */
@Named
@Stateless
public class BookService implements Serializable {
    private static final Logger LOG = Logger.getLogger(BookService.class);

    @Inject
    private BookPersistence persistence;
    @Inject
    private Configuration configuration;

    /**
     * Fetches all books form the persistens
     *
     * @return List of all Books
     */
    @PermitAll
    public List<Book> fetchAllBooks() {
        return persistence.fetchAllBooks();
    }

    /**
     * Fetches all books from the persistens, when the title of the book contains the given string
     *
     * @param title
     * @return List of all books which contain title
     */
    @PermitAll
    public List<Book> fetchAllBooksByTitle(String title) {
        return persistence.fetchAllBooksByTitle(title);
    }

    /**
     * Fetches the book with the given id form the persistence, if id under null throws @IllegalArgumentException
     *
     * @param id
     * @return Book with the given id
     */
    @PermitAll
    public Book fetchBookById(long id) {
        return persistence.fetchBookByID(id);
    }

    /**
     * Saves a new book to the persistence
     *
     * @param book
     */
    @RolesAllowed("user")
    public void saveBook(Book book) {
        persistTagsIfNotAlreadyInDatabase(book);
        persistence.saveBook(book);
    }

    /**
     * Updates an already in the database existing book
     *
     * @param book
     */
    @RolesAllowed("user")
    public void updateBook(Book book) {
        persistTagsIfNotAlreadyInDatabase(book);
        persistence.updateBook(book);
    }

    /**
     * Exchanges the Tags of the Book with the Tags in the Database (if exists)
     * Made to make sure a Tag is unique in the Database
     *
     * @param currentBook
     */
    protected void persistTagsIfNotAlreadyInDatabase(Book currentBook) {
        List<Tag> tagsInDatabase = persistence.fetchAllTags();
        List<Tag> tagsWithID = new ArrayList<>(currentBook.getTags().size());
        currentBook.getTags().forEach(tagInList -> {
            Tag databaseTag;
            if (tagsInDatabase.contains(tagInList)) {
                databaseTag = tagsInDatabase.get(tagsInDatabase.indexOf(tagInList));
            } else {
                databaseTag = persistence.saveTag(tagInList);
            }
            tagsWithID.add(databaseTag);
        });

        currentBook.setTags(tagsWithID);
    }

    /**
     * Uploades the given part to the HDD, change targetdirectory if the file is a book
     *
     * @param part
     * @param isBook
     * @return path of the uploaded file
     */
    @RolesAllowed("user")
    public Path uploadFile(Part part, boolean isBook) {
        Path filePath = null;
        if (part == null) {
            return null;
        }
        try {
            String uniqueFileName = createUniqueFileName(part.getSubmittedFileName());
            filePath = getFilePathForFileType(isBook);
            filePath = filePath.resolve(uniqueFileName);
            //Create Directory if not already exists
            if (!Files.isDirectory(filePath)) {
                Files.createDirectories(filePath);
            }
            Files.copy(part.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            if (LOG.isInfoEnabled()) {
                LOG.info("Successly saved File: " + filePath.toAbsolutePath().toString());
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return filePath;
    }

    private Path getFilePathForFileType(boolean isBook) {
        Path filePath;
        if (isBook) {
            filePath = configuration.getBasePath().resolve("books");
        } else {
            filePath = configuration.getBasePath().resolve("images");
        }
        return filePath;
    }

    /**
     * Create a Random Filename for the file on the server (protection from accidentilly overrride)
     *
     * @param fileName
     * @return
     */
    private String createUniqueFileName(String fileName) {
        int beginIndex = fileName.lastIndexOf(".");
        Random random = new Random();
        if (beginIndex < 0) {
            return fileName + "_" + Math.abs(random.nextLong());
        }
        return fileName.substring(0, beginIndex) + "_" + Math.abs(random.nextLong()) + fileName.substring(beginIndex);
    }

    /**
     * Deletes the given book form the persistence
     *
     * @param book
     */
    @RolesAllowed("user")
    public void removeBook(Book book) {
        removeFiles(book);
        persistence.removeBook(book);
    }

    private void removeFiles(Book book) {
        DataFileLocation dataFileLocation = book.getData();
        if (dataFileLocation != null) {
            try {
                Files.deleteIfExists(Paths.get(dataFileLocation.getFullFilePath()));
                Files.deleteIfExists(Paths.get(dataFileLocation.getFullImagePath()));
            } catch (IOException e) {
                LOG.error("Couldn't delete Files of Book: " + book.getTitle() + ": " + e.getMessage(), e);
            }
        }
    }

    public BookPersistence getPersistence() {
        return persistence;
    }

    public void setPersistence(BookPersistence persistence) {
        this.persistence = persistence;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
