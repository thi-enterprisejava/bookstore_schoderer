package de.schoderer.bookstore.utils.validator;

import de.schoderer.bookstore.testUtils.validator.StubRessourceBundle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.faces.context.FacesContext;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;

/**
 * Created by michael on 07.01.2016.
 */
@RunWith(Theories.class)
@Category(ValidatorTest.class)
public class EmailValidatorTest {
    @DataPoints
    public static String[] test_emails_valid = new String[]{"testabc.de", "test.over@abccom", null, "Test_ABDFOPosfvjclye", "123"};
    private static FacesContext mockedContext;
    private static ResourceBundle bundle;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        mockedContext = mock(FacesContext.class);
        //Create a stub for testing
        bundle = new StubRessourceBundle();
    }

    //TODO multiple datapoints in a test possible??
    @Test
    public void testIfEmailIsValid() {
        String[] test_emails_valid = new String[]{"test@abc.de", "test.over@abc.com", "valid@e-mail.com", "Test_ABDFOP@osfvjcly.de"};
        EmailValidator validator = new EmailValidator();
        validator.setBundle(bundle);


        Stream.of(test_emails_valid).forEach(email -> {
            try {
                validator.validate(mockedContext, null, email);
            } catch (Exception e) {
                Assert.fail("No exception should be thrown for email: " + email + " - " + e);
            }
        });
    }

    @Theory
    public void testIfExceptionIsThrownIfEmailIsNotValid(String invalidEmail) {
        EmailValidator validator = new EmailValidator();
        validator.setBundle(bundle);

        exception.expect(javax.faces.validator.ValidatorException.class);


        validator.validate(mockedContext, null, invalidEmail);
    }
}
