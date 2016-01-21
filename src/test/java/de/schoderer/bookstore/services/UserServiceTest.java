package de.schoderer.bookstore.services;

import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.domain.security.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

/**
 * Created by Michael Schoderer on 21.01.2016.
 */
@Category(ServiceTest.class)
public class UserServiceTest {
    private static UserService userService;

    @BeforeClass
    public static void setUp() {
        userService = new UserService();
        UserPersistence persistence = mock(UserPersistence.class);
        userService.setPersistence(persistence);
    }

    @Test
    public void ifUserCanBeSaved() {
        User user = new User("test@test.de", "secret");

        userService.saveUser(user);

        Mockito.verify(userService.getPersistence(), times(1)).saveUser(user);
    }

    @Test
    public void ifEmailCanBeCheckedIfAlreadyExists() {
        String email = "test@test.de";

        userService.checkIfEmailIsAlreadyRegistered(email);

        Mockito.verify(userService.getPersistence(), times(1)).checkIfEmailIsAlreadyRegistered(email);
    }

}
