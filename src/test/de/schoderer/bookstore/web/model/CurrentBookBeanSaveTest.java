package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.services.BookService;
import de.schoderer.bookstore.testUtils.TestFileRule;
import de.schoderer.bookstore.testUtils.web.model.BookFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;

/**
 * Created by michael on 05.01.2016.
 */
@Category(BeanTest.class)
public class CurrentBookBeanSaveTest {
    private static CurrentBookBean bean;
    @Rule
    public TestFileRule testFileRule_ = new TestFileRule();
    @Rule
    public BookFixture bookFixture = new BookFixture();

    @Before
    public void setUp() {
        bean = new CurrentBookBean();
    }

    @Test
    public void ifNewBookIsReturnedIfIdWasEmpty() {
        bean.setId(null);

        bean.doSetCurrentBook();

        Assert.assertNull(bean.getCurrentBook().getId());
    }

    @Test
    public void ifBookIsSetWithId() {
        bean.setId(null);
        BookService service = Mockito.mock(BookService.class);
        Book book = bookFixture.createBookInstance();
        Mockito.when(service.fetchBookByID(book.getId())).thenReturn(book);
        bean.setBookService(service);
        bean.setId(book.getId());

        bean.doSetCurrentBook();

        Assert.assertThat(bean.getCurrentBook().getId(), is(book.getId()));
        Assert.assertThat(bean.getCurrentBook(), is(book));
    }

    @Test
    public void ifBookIsUpdated() {
        Book bookWithID = bookFixture.createBookInstance();
        BookService service = Mockito.mock(BookService.class);
        bean.setBookService(service);
        bean.setId(bookWithID.getId());
        bean.setCurrentBook(bookWithID);

        bean.doSave();

        Mockito.verify(service, Mockito.times(1)).updateBook(bookWithID);
    }

    @Test
    public void ifNewBookIsSaved() {
        Book bookWithoutID = bookFixture.createBookInstance();
        BookService service = Mockito.mock(BookService.class);
        bean.setBookService(service);
        bookWithoutID.setId(null);
        bean.setId(bookWithoutID.getId());
        bean.setCurrentBook(bookWithoutID);

        bean.doSave();

        Mockito.verify(service, Mockito.times(1)).saveBook(bookWithoutID);
    }


}
