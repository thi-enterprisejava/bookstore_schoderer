package de.schoderer.bookstore.model;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;
import de.schoderer.bookstore.domain.Tag;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by schod on 07.11.2015.
 */
@Named
@RequestScoped
public class CurrentBookBean implements Serializable {

    private BookPersistence persistence;
    private String tag;

    private long id = -1;

    private Book currentBook;

    @Inject
    public CurrentBookBean(BookPersistence persistence) {
        this.persistence = persistence;
        currentBook = new Book();
    }

    public void doSetCurrentBook() {
        if (id > 0) {
            currentBook = persistence.fetchBookByID(id);
        } else {
            currentBook = new Book();
        }
    }


    public void doAddTag() {
        if (!"".equals(tag)) {
            currentBook.getTags().add(new Tag(tag));
            tag = "";
        }
    }

    public void doSave() {
        persistence.saveBook(currentBook);
        currentBook = new Book();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


}
