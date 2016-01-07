package de.schoderer.bookstore.testUtils;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
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
        currentBookBean.setPersistence(Mockito.mock(BookPersistence.class));
        currentBookBean.setPageSwitcher(Mockito.mock(PageSwitcherBean.class));
        return currentBookBean;
    }
}
