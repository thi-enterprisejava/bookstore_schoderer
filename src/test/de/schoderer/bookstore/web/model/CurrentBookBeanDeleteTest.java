package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.utils.CurrentBookBeanFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by michael on 31.12.2015.
 */
public class CurrentBookBeanDeleteTest {
    @Rule
    public CurrentBookBeanFactory dataLocationTestRule = new CurrentBookBeanFactory();

    @Test
    public void ifBookAndFilesAreDeleted() throws IOException {
        CurrentBookBean currentBookBean = dataLocationTestRule.createCurrentBookMock();
        BookPersistence persistence = currentBookBean.getPersistence();
        currentBookBean.deleteBook();
        Mockito.verify(persistence, Mockito.times(1)).removeBook(currentBookBean.getCurrentBook());
        DataFileLocation data = currentBookBean.getCurrentBook().getData();
        Assert.assertFalse(Files.exists(Paths.get(data.getFullFilePath())));
        Assert.assertFalse(Files.exists(Paths.get(data.getFullImagePath())));

    }
}
