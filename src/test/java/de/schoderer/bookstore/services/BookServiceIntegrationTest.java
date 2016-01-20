package de.schoderer.bookstore.services;

import de.schoderer.bookstore.db.BasicPersistence;
import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.db.interfaces.impls.BookPersistenceImpl;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.testUtils.AuthenticatedUser;
import de.schoderer.bookstore.testUtils.web.model.BookFixture;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import java.util.List;

/**
 * Created by Michael Schoderer on 20.01.2016.
 */
@RunWith(Arquillian.class)
@Category(IntegrationTest.class)
public class BookServiceIntegrationTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Rule
    public BookFixture bookFixture = new BookFixture();

    @EJB
    private BookService bookService;

    @EJB
    private AuthenticatedUser authenticatedUser;

    @Deployment(testable = true)
    public static WebArchive createWebDeployment() {

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(BookService.class)
                .addClass(BasicPersistence.class)
                .addClass(BookPersistenceImpl.class)
                .addClass(BookPersistence.class)
                .addClass(Configuration.class)
                .addClass(Book.class)
                .addClass(Tag.class)
                .addClass(DataFileLocation.class)
                .addClass(AuthenticatedUser.class)
                .addAsResource("integration_persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("ds.xml");
        System.out.println(archive.toString(Formatters.VERBOSE));
        return archive;
    }

    @Test
    public void thatBookCanBeSavedAsAuthenticatedUser() throws Exception {
        authenticatedUser.execute(() -> {
            Book book = bookFixture.createBookInstance();

            bookService.saveBook(book);

            List<Book> bookList = bookService.fetchAllBooksByTitle(book.getTitle());
            Assert.assertFalse(bookList.isEmpty());
            Assert.assertNotNull(bookList.get(0).getId());


            return null;
        });
    }

    @Test
    public void thatBookCanNotBeSavedAsUnauthorizedUser() throws Exception {
        Book book = bookFixture.createBookInstance();

        exception.expect(EJBAccessException.class);

        bookService.saveBook(book);
    }

    @Test
    public void thatBookCanBeUpdatedAsAuthorizedUser() throws Exception {
        authenticatedUser.execute(() -> {
            Book book = bookFixture.createBookInstance();
            bookService.saveBook(book);
            Long id = book.getId();
            book.setTitle("HalooNeuesBuch");

            bookService.updateBook(book);

            List<Book> bookList = bookService.fetchAllBooksByTitle(book.getTitle());
            Assert.assertFalse(bookList.isEmpty());
            Assert.assertEquals(id, bookList.get(0).getId());

            return null;
        });
    }

    @Test
    public void thatBookCanNotBeUpdatedAsUnAuthorizedUser() throws Exception {
        Book book = bookFixture.createBookInstance();

        exception.expect(EJBAccessException.class);

        bookService.updateBook(book);
    }

    @Test
    public void thatBookCanBeDeletedAsAuthorizedUser() throws Exception {
        Book book = bookFixture.createBookInstance();
        book.setData(null);
        authenticatedUser.execute(() -> {
            bookService.saveBook(book);

            bookService.removeBook(book);


        List<Book> list = bookService.fetchAllBooksByTitle(book.getTitle());

        Assert.assertTrue(list.isEmpty());
            return null;
        });
    }

    @Test
    public void thatBookCanNotBeDeletedAsUnAuthorizedUser() throws Exception {
        Book book = bookFixture.createBookInstance();
        book.setData(null);
        authenticatedUser.execute(() -> {
            bookService.saveBook(book);
            return null;
        });

        exception.expect(EJBAccessException.class);

        bookService.removeBook(book);
    }


}
