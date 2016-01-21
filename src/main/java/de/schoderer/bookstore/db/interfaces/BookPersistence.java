package de.schoderer.bookstore.db.interfaces;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.Tag;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michael Schoderer on 23.10.15.
 * <p>
 * Simple interface, which provides the CRUD-Methods for the persistence
 */
public interface BookPersistence extends Serializable {
    /**
     * Returns all books in the DB
     *
     * @return List of books
     */
    List<Book> fetchAllBooks();

    /**
     * Fetch all Books where the title contains the given string
     *
     * @param title
     * @return List of books containg title
     */
    List<Book> fetchAllBooksByTitle(String title);

    /**
     * Fetch a book with the given id, if exists
     *
     * @param id
     * @return the book with the id
     */
    Book fetchBookByID(long id);

    /**
     * Save the book to the database
     *
     * @param book
     * @return saved book with filled id
     */
    Book saveBook(Book book);

    /**
     * Merge the given book
     *
     * @param book
     * @return the book
     */
    Book updateBook(Book book);

    /**
     * Remove the given book from the database
     *
     * @param book
     */
    void removeBook(Book book);

    /**
     * Save the given tag to the database
     *
     * @param tag
     * @return saved tag with filled id
     */
    Tag saveTag(Tag tag);

    /**
     * Fetch a tag with the given id, if exists
     *
     * @param id
     * @return
     */
    Tag fetchTagByID(long id);

    /**
     * Find a Tag by the given name
     *
     * @param name
     * @return the tag with the name
     */
    Tag fetchTagByName(String name);

    /**
     * Find a list of all existing tags
     *
     * @return list of existing tags
     */
    List<Tag> fetchAllTags();
}
