package de.schoderer.bookstore.model;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;
import de.schoderer.bookstore.domain.Tag;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by schod on 07.11.2015.
 */
@Named
@SessionScoped
public class AddBookBean implements Serializable {

    private BookPersistence persistence;
    private String tag;


    private Book newBook;

    @Inject
    public AddBookBean(BookPersistence persistence) {
        this.persistence = persistence;
        newBook = new Book();
    }


    public void doAddTag() {
        if (!"".equals(tag)) {
            newBook.getTags().add(new Tag(tag));
            tag = "";
        }
    }

    public void doSave(){
        persistence.saveBook(newBook);
        newBook = new Book();
    }

    public Book getNewBook() {
        return newBook;
    }

    public void setNewBook(Book newBook) {
        this.newBook = newBook;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


}
