package de.schoderer.bookstore.model;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by schod on 05.11.2015.
 */
@Named
@SessionScoped
public class SearchBean implements Serializable {
    @Inject
    private BookPersistence persistence;




    public List<Book> fetchAllBooks(){
        return persistence.fetchAllBooks();
    }
}
