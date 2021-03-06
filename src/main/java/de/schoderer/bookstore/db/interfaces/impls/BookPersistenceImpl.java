package de.schoderer.bookstore.db.interfaces.impls;

import de.schoderer.bookstore.db.BasicPersistence;
import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.Tag;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Michael Schoderer on 21.11.2015.
 */
@Stateless
public class BookPersistenceImpl extends BasicPersistence implements BookPersistence {
    private static final Logger LOG = LogManager.getLogger(BookPersistenceImpl.class);

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
    public Book fetchBookByID(long id) {
        return getEntityManager().find(Book.class, id);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        getEntityManager().persist(book);
        LOG.info("Saved Book: " + book);
        return book;
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        LOG.info("Updated Book: " + book);
        return getEntityManager().merge(book);
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
        LOG.info("Removed Book: " + book);
        getEntityManager().remove(book);
    }

    @Override
    public Tag saveTag(Tag tag) {
        getEntityManager().persist(tag);
        LOG.info("Added Tag: " + tag);
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
