package de.schoderer.bookstore.db.interfaces;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.utils.interceptor.TimeLogging;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.io.Serializable;
import java.util.List;

/**
 * Created by michael on 23.10.15.
 * <p>
 * Simple interface, which provides the CRUD-Methods for the persistence
 */
@TimeLogging
public interface BookPersistence extends Serializable {
    /**
     * Returns all books in the DB
     * @return List of books
     */
    @PermitAll
    List<Book> fetchAllBooks();

    /**
     * Fetch all Books where the title contains the given string
     * @param title
     * @return List of books containg title
     */
    @PermitAll
    List<Book> fetchAllBooksByTitle(String title);

    /**
     * Returns all books with a specific Tag
     * @param tagId
     * @return List of books with the given tag
     */
    @PermitAll
    List<Book> fetchAllBooksWithTagID(long tagId);

    /**
     * Fetch a book with the given id, if exists
     * @param id
     * @return the book with the id
     */
    @PermitAll
    Book fetchBookByID(long id);

    /**
     * Save the book to the database
     * @param book
     * @return saved book with filled id
     */
    @RolesAllowed(value = "user")
    Book saveBook(Book book);

    /**
     * Merge the given book
     * @param book
     * @return the book
     */
    @RolesAllowed(value = "user")
    Book updateBook(Book book);

    /**
     * Remove the given book from the database
     * @param book
     */
    @RolesAllowed(value = "user")
    void removeBook(Book book);

    /**
     * Save the given tag to the database
     * @param tag
     * @return saved tag with filled id
     */
    @RolesAllowed(value = "user")
    Tag saveTag(Tag tag);

    /**
     * Fetch a tag with the given id, if exists
     * @param id
     * @return
     */
    @PermitAll
    Tag fetchTagByID(long id);

    /**
     * Find a Tag by the given name
     * @param name
     * @return the tag with the name
     */
    @PermitAll
    Tag fetchTagByName(String name);

    /**
     * Find a list of all existing tags
     * @return list of existing tags
     */
    @PermitAll
    List<Tag> fetchAllTags();
}
