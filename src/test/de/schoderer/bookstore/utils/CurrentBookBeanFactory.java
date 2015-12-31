package de.schoderer.bookstore.utils;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.web.model.CurrentBookBean;
import de.schoderer.bookstore.web.model.PageSwitcherBean;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

/**
 * Created by michael on 31.12.2015.
 */
public class CurrentBookBeanFactory extends TestFileRule{
    public CurrentBookBean createCurrentBookMock() throws IOException{
        CurrentBookBean currentBookBean = new CurrentBookBean();
        currentBookBean.setPersistence(Mockito.mock(BookPersistence.class));
        currentBookBean.setPageSwitcher(Mockito.mock(PageSwitcherBean.class));
        currentBookBean.setCurrentBook(getBook());
        return currentBookBean;
    }




    public Book getBook() throws IOException{
        Book book = new Book("Test", "Test", "ABC", 2015, "ADFASFASf");
        book.setData(getDataFileLocation());
        System.err.println(book.getData());
        return book;
    }


    public DataFileLocation getDataFileLocation() throws IOException {
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
