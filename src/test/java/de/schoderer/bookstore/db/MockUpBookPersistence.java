package de.schoderer.bookstore.db;

import de.schoderer.bookstore.domain.Book;
import de.schoderer.bookstore.domain.Tag;

import java.util.ArrayList;
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
        saveBook(createBook("AwesomeBook", "Mark Random", "Really good book, you won't beleve whats written in there...", 2001, "1234567890", "Awesome", "Book"));
        saveBook(createBook("Cast Away", "Notsure Where", "With this book you get lost", 1999, "1234567891", "Sold out", "Unknown Location"));
        saveBook(createBook("How to create an book title", "Mathew Undecided", "Where you get ideas for YOUR book title", 2009, "65481357", "Tutorial", "Help", "Book"));
        saveBook(createBook("The Alphabet - The True Story", "Richard Bookmaker", "You won't beleve this...", 2015, "123456756"));
        saveBook(createBook("StarFlight", "Konar Futur", "Another space story...", 2015, "2354716"));
        saveBook(createBook("How to build a time machine", "Karin Wanders", "Learn how to build a time machine", 2345, "945667890"));

    }

    protected Book createBook(String title, String author, String description, int year, String isbn, String... tags) {
        Book book = new Book(title, author, description, year, isbn);
        for (String tag : tags) {
            book.getTags().add(new Tag(tag));
        }
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
        return books.stream().filter(book -> book.getTags().stream().anyMatch(listTag -> listTag.getTag().toLowerCase().contains(lowerCaseTag))).collect(Collectors.toList());
    }

    @Override
    public Book fetchBookByID(long id) {
        //FIXME very very dirty....
        return books.get((int) (id));
    }

    @Override
    public void saveBook(Book book) {

        books.add(book);
        book.setId(books.indexOf(book));
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

    @Override
    public void saveTag(Tag tag) {

    }
}
