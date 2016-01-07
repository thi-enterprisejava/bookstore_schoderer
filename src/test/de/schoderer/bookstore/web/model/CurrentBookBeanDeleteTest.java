package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.testUtils.web.model.BookFixture;
import de.schoderer.bookstore.testUtils.web.model.CurrentBookBeanFactory;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by michael on 31.12.2015.
 */
@Category(BeanTest.class)
public class CurrentBookBeanDeleteTest {
    @Rule
    public CurrentBookBeanFactory currentBookBeanFactory = new CurrentBookBeanFactory();
    @Rule
    public BookFixture bookFixture = new BookFixture();


    @Test
    public void ifBookIsDeleted() throws IOException {
        CurrentBookBean currentBookBean = currentBookBeanFactory.createCurrentBookMock();
        Book bookInstance = bookFixture.createBookMock();
        Mockito.when(bookInstance.getData()).thenReturn(null);
        currentBookBean.setCurrentBook(bookInstance);
        currentBookBean.deleteBook();
        Mockito.verify(currentBookBean.getPersistence(), Mockito.times(1)).removeBook(bookInstance);
    }

    @Test
    public void ifFilesAreDeleted() throws IOException {
        CurrentBookBean currentBookBean = currentBookBeanFactory.createCurrentBookMock();
        Book bookInstance = bookFixture.createBookMock();
        Mockito.when(bookInstance.getData()).thenReturn(bookFixture.createDataFileLocation());
        currentBookBean.setCurrentBook(bookInstance);
        currentBookBean.deleteBook();
        DataFileLocation data = currentBookBean.getCurrentBook().getData();
        Assert.assertFalse(Files.exists(Paths.get(data.getFullFilePath())));
        Assert.assertFalse(Files.exists(Paths.get(data.getFullImagePath())));
    }
}
