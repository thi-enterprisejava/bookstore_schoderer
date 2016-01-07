package de.schoderer.bookstore.domain.book;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Michael Schoderer on 23.10.15.
 */
@Entity
@NamedQueries({

        @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
        @NamedQuery(name = "Book.findByName", query = "SELECT b FROM Book b WHERE b.title LIKE :booktitle")
        // @NamedQuery(name = "Book.findBooksWithTagId", query = "SELECT b FROM Book b WHERE b.id IN (SELECT bt.id FROM Book bt WHERE bt.tags = :tagid)")
})
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;
    private String author;
    @Column(length = 2000)
    private String description;
    private int publishedYear;
    @Column(length = 20)
    private String isbn;

    @OneToOne(cascade = CascadeType.ALL)
    private DataFileLocation data;

    @ManyToMany(targetEntity = Tag.class, fetch = FetchType.EAGER)
    private List<Tag> tags;


    public Book() {
        tags = new ArrayList<>();
        //Set the year to the actual year
        publishedYear = LocalDate.now().getYear();
    }

    public Book(String title, String author, String description, int publishedYear, String isbn) {
        this();
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishedYear = publishedYear;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public DataFileLocation getData() {
        return data;
    }

    public void setData(DataFileLocation data) {
        this.data = data;
    }


    public List<Tag> getTags() {
        return (tags);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn);
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", publishedYear=" + publishedYear +
                ", isbn='" + isbn + '\'' +
                ", data=" + data +
                ", tags=" + tags +
                '}';
    }
}
