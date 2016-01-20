package de.schoderer.bookstore.testUtils.web.model;

import de.schoderer.bookstore.services.BookService;
import de.schoderer.bookstore.testUtils.TestFileRule;
import de.schoderer.bookstore.web.model.CurrentBookBean;
import de.schoderer.bookstore.web.model.PageSwitcherBean;
import org.mockito.Mockito;

import java.io.IOException;

/**
 * Created by michael on 31.12.2015.
 */
public class CurrentBookBeanFactory extends TestFileRule {
    public CurrentBookBean createCurrentBookMock() throws IOException {
        CurrentBookBean currentBookBean = new CurrentBookBean();
        currentBookBean.setBookService(Mockito.mock(BookService.class));
        currentBookBean.setPageSwitcher(Mockito.mock(PageSwitcherBean.class));
        return currentBookBean;
    }
}
