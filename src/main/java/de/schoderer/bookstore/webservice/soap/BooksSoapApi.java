package de.schoderer.bookstore.webservice.soap;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by schod on 25.10.2015.
 */
//TODO specific path for the webserivce??
@Stateless
@WebService
public class BooksSoapApi {
    @Inject
    private BookPersistence persistence;


    public List<Book> getAllBooks() {
        return persistence.fetchAllBooks();
    }

    public List<Book> getBooksByTag(String tag) {
        return persistence.fetchAllBooksByTag(tag);
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
