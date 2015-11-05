package de.schoderer.bookstore.db;

import de.schoderer.bookstore.domain.Book;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by schod on 25.10.2015.
 */
public class MockUpBookPersistence implements BookPersistence {
    private static final List<Book> books = new ArrayList<>();


    public MockUpBookPersistence() {
        fillList();
    }

    private void fillList() {
        books.add(createBook("AwesomeBook", "Mark Random", "Really good book, you won't beleve whats written in there...", 2001, "1234567890", "Awesome", "Book"));
        books.add(createBook("Cast Away", "Notsure Where", "With this book you get lost", 1999, "1234567891", "Sold out", "Unknown Location"));
        books.add(createBook("How to create an book title", "Mathew Undecided", "Where you get ideas for YOUR book title", 2009, "65481357", "Tutorial", "Help", "Book"));
        books.add(createBook("The Alphabet - The True Story", "Richard Bookmaker", "You won't beleve this...", 2015, "123456756"));
        books.add(createBook("StarFlight", "Konar Futur", "Another space story...", 2015, "2354716"));
        books.add(createBook("How to build a time machine", "Karin Wanders", "Learn how to build a time machine", 2345, "945667890"));
        Collections.shuffle(books);

    }

    protected Book createBook(String title, String author, String description, int year, String isbn, String... tags) {
        Book book = new Book(title, author, description, year, isbn);
        book.getTags().addAll(Arrays.asList(tags));
        return book;

    }

    @Override
    public List<Book> fetchAllBooks() {
        return books;
    }

    @Override
    public List<Book> fetchAllBooksByTitle(String title) {
        String lowerCaseTitle = title.toLowerCase();
        return books.stream().filter(book -> book.getTitle().toLowerCase().contains(lowerCaseTitle)).collect(Collectors.toList());
    }

    @Override
    public List<Book> fetchAllBooksByTag(String tag) {
        String lowerCaseTag = tag.toLowerCase();
        return books.stream().filter(book -> book.getTags().stream().anyMatch(listTag -> listTag.toLowerCase().contains(lowerCaseTag))).collect(Collectors.toList());
    }

    @Override
    public void saveBook(Book book) {
        books.add(book);
    }

    @Override
    public void updateBook(Book book) {
        removeBook(book);
        saveBook(book);
        //FIXME maybe its better to combine save and update in one method...
    }

    @Override
    public void removeBook(Book book) {
        if (books.contains(book)) {
            books.remove(book);
        }
    }
}
