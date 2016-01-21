package de.schoderer.bookstore.webservice.soap;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.services.BookService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by Michael Schoderer on 25.10.2015.
 */
@Stateless
@WebService
public class BooksSoapApi {
    @Inject
    private BookService bookService;

    public List<Book> getAllBooks() {
        return bookService.fetchAllBooks();
    }

    public List<Book> getBooksByTitle(String title) {
        return bookService.fetchAllBooksByTitle(title);
    }

    public void updateBook(Book book) {
        bookService.updateBook(book);
    }

    public void saveBook(Book book) {
        bookService.saveBook(book);
    }

    public void deleteBook(Book book) {
        bookService.removeBook(book);
    }

}
