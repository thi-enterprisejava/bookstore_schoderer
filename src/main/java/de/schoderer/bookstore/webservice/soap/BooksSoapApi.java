package de.schoderer.bookstore.webservice.soap;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;

import java.util.List;

/**
 * Created by schod on 25.10.2015.
 */
//TODO specific path for the webserivce??
public class BooksSoapApi {
    private BookPersistence persistence;


    public List<Book> getAllBooks() {
        return persistence.fetchAllBooks();
    }


    public List<Book> getBooksByTitle(String title) {
        return persistence.fetchAllBooksByTitle(title);
    }

    public void updateBook(Book book) {
        persistence.updateBook(book);
    }

    public void saveBook(Book book) {
        persistence.saveBook(book);
    }

    public void deleteBook(Book book) {
        persistence.removeBook(book);
    }
}
