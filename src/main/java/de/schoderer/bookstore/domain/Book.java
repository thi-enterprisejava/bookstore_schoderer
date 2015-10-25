package de.schoderer.bookstore.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 23.10.15.
 */
public class Book implements Serializable{
    private String title;
    private String author;
    private String description;
    private int publishedYear;
    private String isbn;
    private List<String> tags;


    public Book() {
        tags = new ArrayList<>();
    }

    public Book(String title, String author, String description, int publishedYear, String isbn) {
        this();
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishedYear = publishedYear;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", publishedYear=" + publishedYear +
                ", isbn='" + isbn + '\'' +
                ", tags=" + tags +
                '}';
    }
}
