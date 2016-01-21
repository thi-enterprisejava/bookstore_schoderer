package de.schoderer.bookstore.utils.validator;

import de.schoderer.bookstore.services.UserService;
import de.schoderer.bookstore.testUtils.validator.StubRessourceBundle;
import de.schoderer.bookstore.utils.ExternalComponents;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import javax.faces.context.FacesContext;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Michael Schoderer on 21.01.2016.
 */
@Category(ValidatorTest.class)
public class EmailValidatorTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private EmailValidator validator;
    private FacesContext mockedContext = mock(FacesContext.class);

    public static EmailValidator createValidatorWithMockedDependencies() {
        EmailValidator validator = new EmailValidator();
        validator.setBundle(new StubRessourceBundle());
        validator.service = mock(UserService.class);

        validator.utils = mock(ExternalComponents.class);
        when(validator.utils.getResourceBundleStringInCurrentLocal(anyString())).thenReturn("test");
        return validator;
    }

    @Before
    public void setUp() {
        validator = createValidatorWithMockedDependencies();
    }

    @Test
    public void ifExceptionIsThrownIfEmailIsAlreadyRegistered() {
        when(validator.service.checkIfEmailIsAlreadyRegistered("test@test.de")).thenReturn(true);

        exception.expect(javax.faces.validator.ValidatorException.class);

        validator.validate(mockedContext, null, "test@test.de");
    }
}
