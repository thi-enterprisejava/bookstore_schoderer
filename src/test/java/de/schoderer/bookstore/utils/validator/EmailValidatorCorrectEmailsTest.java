package de.schoderer.bookstore.utils.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.categories.Category;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.faces.context.FacesContext;

import static org.mockito.Mockito.mock;

/**
 * Created by michael on 07.01.2016.
 */
@RunWith(Theories.class)
@Category(ValidatorTest.class)
public class EmailValidatorCorrectEmailsTest {
    @DataPoints
    public static String[] test_emails_inValid = new String[]{"test@abc.de", "test.over@abc.com", "valid@e-mail.com", "Test_ABDFOP@osfvjcly.de"};
    private static FacesContext mockedContext;
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private EmailValidator validator;

    @Before
    public void setUp() {
        validator = EmailValidatorTest.createValidatorWithMockedDependencies();
        mockedContext = mock(FacesContext.class);
    }

    @Theory
    public void testIfEmailIsValid(String email) {

        try {
            validator.validate(mockedContext, null, email);
        } catch (Exception e) {
            Assert.fail("No exception should be thrown for email: " + email + " - " + e);
        }

    }


}
