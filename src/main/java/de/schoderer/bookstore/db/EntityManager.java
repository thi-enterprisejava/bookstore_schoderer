package de.schoderer.bookstore.db;

import de.schoderer.bookstore.domain.Book;
import de.schoderer.bookstore.utils.interceptor.TimeLogging;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by schod on 21.11.2015.
 */
@TimeLogging
@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EntityManager implements BookPersistence {

    @PersistenceContext
    private javax.persistence.EntityManager em;

    @Override
    public List<Book> fetchAllBooks() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book as b", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> fetchAllBooksByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book as b where b.title like :booktitle", Book.class);
        query.setParameter("booktitle", title);
        return query.getResultList();
    }

    @Override
    public List<Book> fetchAllBooksByTag(String tag) {
        // TODO
        // /TypedQuery<Book> query = em.createQuery("SELECT b FROM Book as b where b.tags = :booktitle", Book.class);
        //query.setParameter("booktitle", title);
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
    @Transactional
    public void updateBook(Book book) {
        em.merge(book);
    }

    @Override
    public void removeBook(Book book) {
        em.remove(book);
    }
}
