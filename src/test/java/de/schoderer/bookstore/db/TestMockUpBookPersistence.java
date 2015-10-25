package de.schoderer.bookstore.db;

 import static org.junit.Assert.*;

 import de.schoderer.bookstore.domain.Book;
 import org.junit.BeforeClass;
import org.junit.Test;

 import java.util.List;

/**
 * Created by schod on 25.10.2015.
 */
public class TestMockUpBookPersistence {
    private static BookPersistence persistence;

    @BeforeClass
    public static void setUp(){
        persistence = new MockUpBookPersistence();
    }

    @Test
    public void testNotEmpty(){
        assertFalse("List is empty", persistence.fetchAllBooks().isEmpty());
    }
    @Test
    public void testSaveBook(){
        Book book = new Book("Book of Testing", "Konor Test", "Wow all those tests", 2014, "9257004");
        persistence.saveBook(book);
        assertTrue("Book is not in the List", persistence.fetchAllBooks().contains(book));
    }
    @Test
    public void testFetchBookByTitle(){
        Book book = new Book("Book of Testing", "Konor Test", "Wow all those tests", 2014, "9257004");
        persistence.saveBook(book);
        String search = "Book";
        List<Book> searchedBooks = persistence.fetchAllBooksByTitle(search);
        assertFalse("No books found",searchedBooks.isEmpty());
        searchedBooks.forEach(foundBooks -> {
            assertTrue("Book dosent has \""+search+"\" in title: "+book.getTitle(), foundBooks.getTitle().toLowerCase().contains(search.toLowerCase()));
        });
    }
    @Test
    public void testFetchBookByTag(){
        Book book = new Book("Book of Testing", "Konor Test", "Wow all those tests", 2014, "9257004");
        String search = "Test";
        book.getTags().add(search);
        persistence.saveBook(book);
        List<Book> searchedBooks = persistence.fetchAllBooksByTag(search);
        assertFalse("No books found",searchedBooks.isEmpty());
        searchedBooks.forEach(foundBooks -> {
            assertTrue("Book dosent has \""+search+"\" as tag", foundBooks.getTitle().toLowerCase().contains(search.toLowerCase()));
        });
    }

    @Test
    public void testRemoveBook(){
        Book book = new Book("Book of Testing", "Konor Test", "Wow all those tests", 2014, "9257004");
        persistence.saveBook(book);
        persistence.removeBook(book);
        assertFalse(persistence.fetchAllBooks().contains(book));

    }


}
