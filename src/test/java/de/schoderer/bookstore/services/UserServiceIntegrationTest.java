package de.schoderer.bookstore.services;

import de.schoderer.bookstore.db.BasicPersistence;
import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.db.interfaces.impls.BookPersistenceImpl;
import de.schoderer.bookstore.db.interfaces.impls.UserPersistenceImpl;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.domain.security.User;
import de.schoderer.bookstore.domain.security.UserRole;
import de.schoderer.bookstore.testUtils.AuthenticatedUser;
import de.schoderer.bookstore.testUtils.web.model.BookFixture;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 * Created by Michael Schoderer on 20.01.2016.
 */
@RunWith(Arquillian.class)
@Category(IntegrationTest.class)
public class UserServiceIntegrationTest {


    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @EJB
    private UserService userService;

    @EJB
    private AuthenticatedUser authenticatedUser;

    @Deployment(testable = true)
    public static WebArchive createWebDeployment(){

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(UserService.class)
                .addClass(BasicPersistence.class)
                .addClass(UserPersistence.class)
                .addClass(UserPersistenceImpl.class)
                .addClass(UserRole.class)
                .addClass(User.class)
                .addClass(AuthenticatedUser.class)
                .addAsResource("integration_persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("book_integration_test_ds.xml")
                ;
        System.out.println(archive.toString(Formatters.VERBOSE));
        return archive;
    }

    @Test
    public void thatUserCanBeSavedWithNoRights() throws Exception{
        User user = createUser();

        userService.saveUser(user);
    }




    private User createUser(){
        User user = new User("test@test.de", "strongPW");
        user.getUserRoles().add(new UserRole("user"));
        return user;
    }
}
