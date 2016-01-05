package de.schoderer.bookstore.db.interfaces.impls;

import de.schoderer.bookstore.db.BasicPersistence;
import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.Tag;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by schod on 21.11.2015.
 */
@Named
@Stateless
public class BookPersistenceImpl extends BasicPersistence implements BookPersistence {


    @Override
    public List<Book> fetchAllBooks() {
        TypedQuery<Book> query = getEntityManager().createNamedQuery("Book.findAll", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> fetchAllBooksByTitle(String title) {
        TypedQuery<Book> query = getEntityManager().createNamedQuery("Book.findByName", Book.class);
        query.setParameter("booktitle", "%" + title + "%");
        return query.getResultList();
    }

    @Override
    public List<Book> fetchAllBooksWithTagID(long tagId) {
        //TODO implementieren
        //TypedQuery<Book> query = em.createNamedQuery("Book.findBooksWithTagId", Book.class);
        //query.setParameter("tagid", tagId);
        return new ArrayList<>();
    }

    @Override
    public Book fetchBookByID(long id) {
        return getEntityManager().find(Book.class, id);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        getEntityManager().persist(book);
        return book;
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        //Check if book is in entity-contex, if not add it
        if (!getEntityManager().contains(book)) {
            book = getEntityManager().merge(book);
        }
        getEntityManager().merge(book);
        return book;
    }

    /**
     * Check if the book is in the current transaction context, if not merge it in the current context
     *
     * @param book
     */
    @Override
    public void removeBook(Book book) {
        //Check if book is attachted to Entity-Manager, if not attach it
        if (!getEntityManager().contains(book)) {
            book = getEntityManager().merge(book);
        }
        getEntityManager().remove(book);
    }

    @Override
    public Tag saveTag(Tag tag) {
        getEntityManager().persist(tag);
        return tag;
    }

    @Override
    public Tag fetchTagByID(long id) {
        return getEntityManager().find(Tag.class, id);
    }

    @Override
    public Tag fetchTagByName(String name) {
        TypedQuery<Tag> query = getEntityManager().createNamedQuery("Tag.findByName", Tag.class);
        return query.getSingleResult();
    }

    @Override
    public List<Tag> fetchAllTags() {
        TypedQuery<Tag> query = getEntityManager().createNamedQuery("Tag.findAll", Tag.class);
        return query.getResultList();
    }
}
