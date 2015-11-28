package de.schoderer.bookstore.db;

import de.schoderer.bookstore.domain.Book;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by schod on 21.11.2015.
 */
public class EntityManager implements BookPersistence {

    @PersistenceContext
    private javax.persistence.EntityManager em;


    @Override
    public List<Book> fetchAllBooks() {
        return null;
    }

    @Override
    public List<Book> fetchAllBooksByTitle(String title) {
        return null;
    }

    @Override
    public List<Book> fetchAllBooksByTag(String tag) {
        return null;
    }

    @Override
    public Book fetchBookByID(long id) {
        return em.find(Book.class, id);
    }

    @Override
    @Transactional
    public void saveBook(Book book) {
        em.persist(book);
    }

    @Override
    public void updateBook(Book book) {
        em.merge(book);
    }

    @Override
    public void removeBook(Book book) {
        em.remove(book);
    }
}
