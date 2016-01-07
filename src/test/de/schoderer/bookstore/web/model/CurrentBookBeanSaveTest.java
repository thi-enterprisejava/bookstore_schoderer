package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.testUtils.TestFileRule;
import de.schoderer.bookstore.testUtils.web.model.BookFixture;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;

/**
 * Created by michael on 05.01.2016.
 */
@Category(BeanTest.class)
public class CurrentBookBeanSaveTest {
    @Rule
    public TestFileRule testFileRule_ = new TestFileRule();
    @Rule
    public BookFixture bookFixture = new BookFixture();


    private static CurrentBookBean bean;


    @Before
    public void setUp(){
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
        BookPersistence mockPersistence = setMockPersistence();
        Book book = bookFixture.createBookInstance();
        Mockito.when(mockPersistence.fetchBookByID(book.getId())).thenReturn(book);
        bean.setId(book.getId());

        bean.doSetCurrentBook();

        Assert.assertThat(bean.getCurrentBook().getId(), is(book.getId()));
        Assert.assertThat(bean.getCurrentBook(), is(book));
    }

    @Test
    public void ifBookIsUpdated() {
        Book bookWithID = bookFixture.createBookInstance();
        BookPersistence persistence = setMockPersistence();
        bean.setId(bookWithID.getId());
        bean.setCurrentBook(bookWithID);

        bean.doSave();

        Mockito.verify(persistence, Mockito.times(1)).updateBook(bookWithID);
    }
    @Test
    public void ifNewBookIsSaved() {
        Book bookWithoutID = bookFixture.createBookInstance();
        BookPersistence persistence = setMockPersistence();
        bookWithoutID.setId(null);
        bean.setId(bookWithoutID.getId());
        bean.setCurrentBook(bookWithoutID);

        bean.doSave();

        Mockito.verify(persistence, Mockito.times(1)).saveBook(bookWithoutID);
    }

    private BookPersistence setMockPersistence() {
        BookPersistence mockPersistence = Mockito.mock(BookPersistence.class);
        bean.setPersistence(mockPersistence);
        return mockPersistence;
    }
}
