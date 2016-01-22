package de.schoderer.bookstore.testUtils.web.model;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.testUtils.TestFileRule;
import org.junit.Before;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michael on 04.01.2016.
 */
public class BookFixture extends TestFileRule {

    @Before
    public void setUp() throws Throwable {
        super.before();
    }

    public Book createBookMock() {
        return Mockito.mock(Book.class);
    }


    public List<Book> createBookList(int amount) throws IOException {
        List<Book> bookList = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            bookList.add(createRandomBook());
        }
        return bookList;
    }


    public Book createBookInstance() {
        Book book = createRandomBook();
        DataFileLocation location = Mockito.mock(DataFileLocation.class);
        book.setData(location);
        return book;
    }

    private Book createRandomBook() {
        Random random = new Random();
        return createBook("Hallo" + random.nextInt(), "Author" + random.nextInt(), random.nextInt(2015), "tag" + random.nextInt(), "tag" + random.nextInt(), "tag" + random.nextInt());
    }

    private Book createBook(String name, String author, int year, String... tags) {

        Book book = new Book(name, author, null, year, null);
        book.setId(Math.abs(new Random().nextLong()));
        for (String tag : tags) {
            book.getTags().add(new Tag(tag.toUpperCase().trim()));
        }
        return book;
    }

    public DataFileLocation createDataFileLocation() throws IOException {
        DataFileLocation location = new DataFileLocation();
        File file = getRandomTestFile();
        location.setFullFilePath(file.getAbsolutePath());
        location.setFileName(file.getName());
        file = getRandomTestFile();
        location.setFullImagePath(file.getAbsolutePath());
        location.setImageName(file.getName());
        return location;
    }

}
