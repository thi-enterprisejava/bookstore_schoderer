package de.schoderer.bookstore.services;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.testUtils.TestFileRule;
import de.schoderer.bookstore.testUtils.web.MockPart;
import de.schoderer.bookstore.testUtils.web.model.BookFixture;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;

/**
 * Created by Michael Schoderer on 20.01.2016.
 */
@Category(ServiceTest.class)
public class BookServiceTest {
    public static List<Tag> tagList = Arrays.asList(new Tag("TEST"), new Tag("TAG"), new Tag("JUNIT"));


    @Rule
    public TestFileRule testFileRule_ = new TestFileRule();
    @Rule
    public BookFixture bookFixture = new BookFixture();

    public BookService service;

    @Before
    public void setUp() {
        service = new BookService();
        service.setConfiguration(new Configuration());
        service.setPersistence(Mockito.mock(BookPersistence.class));
    }

    @Test
    public void ifNullIsReturnedWhenBookPartIsNull() throws IOException {
        mockUploadDirecotry();
        Path uploadedFilePath = service.uploadFile(null, true);
        Assert.assertNull(uploadedFilePath);
    }

    @Test
    public void ifNullIsReturnedWhenImagePartIsNull() throws IOException {
        mockUploadDirecotry();
        Path uploadedFilePath = service.uploadFile(null, false);
        Assert.assertNull(uploadedFilePath);
    }

    @Test
    public void ifBookCanBeUploadedToHardDrive() throws IOException {
        mockUploadDirecotry();
        File testFile = testFileRule_.getRandomTestFile();
        Path uploadedFilePath = service.uploadFile(new MockPart(testFile, "text/plain"), true);
        Assert.assertTrue(Files.exists(uploadedFilePath));
        Assert.assertTrue(FileUtils.contentEquals(testFile, uploadedFilePath.toFile()));

    }

    @Test
    public void ifImageCanBeUploadedToHardDrive() throws IOException {
        mockUploadDirecotry();
        File testFile = testFileRule_.getRandomTestFile();
        Path uploadedFilePath = service.uploadFile(new MockPart(testFile, "text/plain"), false);
        Assert.assertTrue(Files.exists(uploadedFilePath));
    }

    private void mockUploadDirecotry() throws IOException {
        Configuration configuration = Mockito.mock(Configuration.class);
        Path path = testFileRule_.newFolder().toPath();
        Files.createDirectory(path.resolve("books"));
        Files.createDirectory(path.resolve("images"));
        Mockito.when(configuration.getBasePath()).thenReturn(path);
    }


    @Test
    public void ifTagsAreSavedIfNotAlreadInDatabase() throws IOException {
        Mockito.when(service.getPersistence().fetchAllTags()).thenReturn(tagList);
        Book book = bookFixture.createBookInstance();
        book.getTags().clear();
        Tag tag = new Tag("BOOK");
        book.getTags().add(tag);
        service.persistTagsIfNotAlreadyInDatabase(book);
        Mockito.verify(service.getPersistence(), times(1)).saveTag(tag);
    }

    @Test
    public void ifTagsAreNotSavedIfAlreadInDatabase() throws IOException {
        Mockito.when(service.getPersistence().fetchAllTags()).thenReturn(tagList);
        Book book = bookFixture.createBookInstance();
        book.getTags().clear();
        Tag tag = new Tag("TEST");
        book.getTags().add(tag);
        service.persistTagsIfNotAlreadyInDatabase(book);
        Mockito.verify(service.getPersistence(), times(0)).saveTag(tag);
    }

    @Test
    public void ifUploadAndSaveFileReturnsRightDataFileLocation() throws IOException {
        Configuration configuration = Mockito.mock(Configuration.class);
        service.setConfiguration(configuration);
        Mockito.when(configuration.getBasePath()).thenReturn(testFileRule_.getRoot().toPath());
        File bookFile = testFileRule_.getRandomTestFile();

        Path path = service.uploadFile(new MockPart(bookFile, "text"), true);

        Assert.assertNotNull(path);
        Assert.assertTrue("Book-file should exist", Files.exists(path));
        Assert.assertTrue(FileUtils.contentEquals(bookFile, path.toFile()));
    }

    @Test
    public void ifFilesAreDeleted() throws IOException {
        Book bookInstance = bookFixture.createBookMock();
        Mockito.when(bookInstance.getData()).thenReturn(bookFixture.createDataFileLocation());
        DataFileLocation data = bookInstance.getData();

        service.removeBook(bookInstance);

        Assert.assertFalse("Files werent deleted", Files.exists(Paths.get(data.getFullFilePath())));
        Assert.assertFalse("Files werent deleted", Files.exists(Paths.get(data.getFullImagePath())));
    }

    @Test
    public void ifFetchAllFromPersistenceIsCalled() {

        service.fetchAllBooks();

        Mockito.verify(service.getPersistence(), times(1)).fetchAllBooks();
    }

    @Test
    public void ifFetchWithTitleFromPersistenceIsCalled() {
        String testTitle = "TestTitle";

        service.fetchAllBooksByTitle(testTitle);

        Mockito.verify(service.getPersistence(), times(1)).fetchAllBooksByTitle(testTitle);
    }

    @Test
    public void ifFetchWithIdFromPersistenceIsCalled() {
        long id = 345L;

        service.fetchBookById(id);

        Mockito.verify(service.getPersistence(), times(1)).fetchBookByID(id);
    }

    @Test
    public void ifBookIsSaved() throws IOException {
        Book bookInstance = bookFixture.createBookMock();
        Mockito.when(bookInstance.getData()).thenReturn(bookFixture.createDataFileLocation());

        service.saveBook(bookInstance);

        Mockito.verify(service.getPersistence(), times(1)).saveBook(bookInstance);
    }

    @Test
    public void ifBookIsUpdated() throws IOException {
        Book bookInstance = bookFixture.createBookMock();
        Mockito.when(bookInstance.getData()).thenReturn(bookFixture.createDataFileLocation());

        service.updateBook(bookInstance);

        Mockito.verify(service.getPersistence(), times(1)).updateBook(bookInstance);
    }

}
