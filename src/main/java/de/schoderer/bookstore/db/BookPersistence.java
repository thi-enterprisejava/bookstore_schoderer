package de.schoderer.bookstore.db;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.utils.interceptor.TimeLogging;

import java.io.Serializable;
import java.util.List;

/**
 * Created by michael on 23.10.15.
 * <p>
 * Simple interface, which provides the CRUD-Methods for the persistence
 */
@TimeLogging
public interface BookPersistence extends Serializable {


    List<Book> fetchAllBooks();

    List<Book> fetchAllBooksByTitle(String title);

    List<Book> fetchAllBooksWithTagID(long tagId);

    Book fetchBookByID(long id);

    Book saveBook(Book book);

    Book updateBook(Book book);

    void removeBook(Book book);


    Tag saveTag(Tag tag);

    Tag fetchTagByID(long id);

    Tag fetchTagByName(String name);

    List<Tag> fetchAllTags();
}
