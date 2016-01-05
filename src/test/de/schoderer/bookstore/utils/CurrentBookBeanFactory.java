package de.schoderer.bookstore.utils;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.web.model.CurrentBookBean;
import de.schoderer.bookstore.web.model.PageSwitcherBean;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by michael on 31.12.2015.
 */
public class CurrentBookBeanFactory extends TestFileRule {
    public CurrentBookBean createCurrentBookMock() throws IOException {
        CurrentBookBean currentBookBean = new CurrentBookBean();
        currentBookBean.setPersistence(Mockito.mock(BookPersistence.class));
        currentBookBean.setPageSwitcher(Mockito.mock(PageSwitcherBean.class));
        return currentBookBean;
    }
}
