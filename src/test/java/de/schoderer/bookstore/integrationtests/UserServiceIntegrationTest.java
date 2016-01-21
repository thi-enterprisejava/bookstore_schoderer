package de.schoderer.bookstore.integrationtests;

import de.schoderer.bookstore.db.BasicPersistence;
import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.db.interfaces.impls.UserPersistenceImpl;
import de.schoderer.bookstore.domain.security.User;
import de.schoderer.bookstore.domain.security.UserRole;
import de.schoderer.bookstore.services.UserService;
import de.schoderer.bookstore.testUtils.AuthenticatedUser;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Random;

/**
 * Created by Michael Schoderer on 20.01.2016.
 */
@RunWith(Arquillian.class)
@Category(IntegrationTest.class)
public class UserServiceIntegrationTest {


    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Inject
    private UserService userService;

    @Inject
    private AuthenticatedUser authenticatedUser;

    @Deployment(testable = true)
    public static WebArchive createWebDeployment() {

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(AuthenticatedUser.class)
                .addClass(IntegrationTest.class)
                .addClass(User.class)
                .addClass(UserRole.class)
                .addClass(UserPersistence.class)
                .addClass(UserPersistenceImpl.class)
                .addClass(BasicPersistence.class)
                .addClass(UserService.class)
                .addAsResource("integration_persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("book-test-ds.xml");
        System.out.println(archive.toString(Formatters.VERBOSE));
        return archive;
    }

    @Test
    public void thatUserCanBeSavedWithNoRights() throws Exception {
        User user = createUser();

        userService.saveUser(user);

        Assert.assertTrue(userService.checkIfEmailIsAlreadyRegistered(user.getEmail()));
    }

    @Test
    public void thatUserEmailCanBeCheckedWithNoRights() throws Exception {
        User user = createUser();

        boolean before = userService.checkIfEmailIsAlreadyRegistered("test@test.de");
        userService.saveUser(user);

        Assert.assertFalse(before);
        Assert.assertTrue(userService.checkIfEmailIsAlreadyRegistered(user.getEmail()));
    }

    private User createUser() {
        Random random = new Random();
        User user = new User("test@" + random.nextInt() + "test.de", "strongPW");
        user.getUserRoles().add(new UserRole("user"));
        return user;
    }
}
