package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.testUtils.BookFixture;
import de.schoderer.bookstore.web.BeanTest;
import org.junit.Assert;
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
    @Rule
    public BookFixture bookFixture = new BookFixture();

    @Test
    public void ifNewBookIsReturnedIfIdWasEmpty() {
        CurrentBookBean bean = new CurrentBookBean(null);
        bean.doSetCurrentBook();
        Assert.assertNull(bean.getCurrentBook().getId());
    }

    @Test
    public void ifBookIsSetWithId() {
        CurrentBookBean bean = new CurrentBookBean(null);
        BookPersistence mockPersistence = Mockito.mock(BookPersistence.class);
        bean.setPersistence(mockPersistence);
        Book book = bookFixture.createBookInstance();
        Mockito.when(mockPersistence.fetchBookByID(book.getId())).thenReturn(book);

        bean.setId(book.getId());
        bean.doSetCurrentBook();
        Assert.assertThat(bean.getCurrentBook().getId(), is(book.getId()));
        Assert.assertThat(bean.getCurrentBook(), is(book));
    }

    @Test
    public void ifBookIsUpdated() {
        Book newBook = bookFixture.createBookInstance();
        CurrentBookBean bean = new CurrentBookBean(newBook.getId());
        bean.setCurrentBook(newBook);
    }
}
