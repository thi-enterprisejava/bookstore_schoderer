package de.schoderer.bookstore.db;

import de.schoderer.bookstore.domain.Book;
import de.schoderer.bookstore.domain.Tag;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by schod on 21.11.2015.
 */
@Named
@Stateless
public class EntityManager implements BookPersistence {

    @PersistenceContext
    private javax.persistence.EntityManager em;

    @Override
    public List<Book> fetchAllBooks() {
        TypedQuery<Book> query = em.createNamedQuery("Book.findAll", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> fetchAllBooksByTitle(String title) {
        TypedQuery<Book> query = em.createNamedQuery("Book.findByName", Book.class);
        query.setParameter("booktitle", title);
        return query.getResultList();
    }

    @Override
    public List<Book> fetchAllBooksWithTagID(long tagId) {
        //TypedQuery<Book> query = em.createNamedQuery("Book.findBooksWithTagId", Book.class);
        //query.setParameter("tagid", tagId);
        return new ArrayList<>();
    }

    @Override
    public Book fetchBookByID(long id) {
        return em.find(Book.class, id);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        if (!em.contains(book)) {
            book = em.merge(book);
        }
        em.merge(book);
        return book;
    }

    /**
     * Check if the book is in the current transaction context, if not merge it in the current context
     * @param book
     */
    @Override
    public void removeBook(Book book) {
        if (!em.contains(book)) {
            book = em.merge(book);
        }
        em.remove(book);
    }

    @Override
    public Tag saveTag(Tag tag) {
        em.persist(tag);
        return tag;
    }

    @Override
    public Tag fetchTagByID(long id) {
        return em.find(Tag.class, id);
    }

    @Override
    public Tag fetchTagByName(String name) {
        TypedQuery<Tag> query = em.createNamedQuery("Tag.findByName", Tag.class);
        return query.getSingleResult();
    }

    @Override
    public List<Tag> fetchAllTags() {
        TypedQuery<Tag> query = em.createNamedQuery("Tag.findAll", Tag.class);
        return query.getResultList();
    }
}
