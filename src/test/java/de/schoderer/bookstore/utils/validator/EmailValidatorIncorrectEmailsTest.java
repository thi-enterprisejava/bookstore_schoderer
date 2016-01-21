package de.schoderer.bookstore.utils.validator;

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
public class EmailValidatorIncorrectEmailsTest {
    @DataPoints
    public static String[] invalidEmails = new String[]{"testabc.de", "test.over@abccom", null, "Test_ABDFOPosfvjclye", "123"};
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
    public void testIfExceptionIsThrownIfEmailIsNotValid(String invalidEmail) {

        exception.expect(javax.faces.validator.ValidatorException.class);

        validator.validate(mockedContext, null, invalidEmail);
    }
}
