package de.schoderer.bookstore.db;

import de.schoderer.bookstore.domain.Book;

import java.io.Serializable;
import java.util.List;

/**
 * Created by michael on 23.10.15.
 * <p>
 * Simple interface, which provides the CRUD-Methods for the persistence
 */
public interface BookPersistence extends Serializable{



    List<Book> fetchAllBooks();

    List<Book> fetchAllBooksByTitle(String title);

    List<Book> fetchAllBooksByTag(String tag);

    Book fetchBookByID(long id);

    void saveBook(Book book);

    void updateBook(Book book);

    void removeBook(Book book);
}
