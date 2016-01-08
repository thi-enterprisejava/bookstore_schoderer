package de.schoderer.bookstore.utils.validator.upload;

import de.schoderer.bookstore.testUtils.validator.StubRessourceBundle;
import de.schoderer.bookstore.utils.validator.ValidatorTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.util.ResourceBundle;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Michael Schoderer on 07.01.2016.
 */
@Category(ValidatorTest.class)
public class PictureUploadValidatorTest {
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

    @Test
    public void happyPath() {
        PictureUploadValidator validator = new PictureUploadValidator();
        validator.setBundle(bundle);
        Part part = mock(Part.class);
        when(part.getContentType()).thenReturn("image/");
        when(part.getSize()).thenReturn(Long.valueOf(0));

        try {
            validator.validate(mockedContext, null, part);
        } catch (Exception e) {
            org.junit.Assert.fail("No exception should be thrown - " + e);
        }
    }

    @Test
    public void ifValidatorExceptionIsThrownIfPartIsNull() {
        PictureUploadValidator validator = new PictureUploadValidator();
        validator.setBundle(bundle);

        exception.expect(javax.faces.validator.ValidatorException.class);

        validator.validate(mockedContext, null, null);
    }

    @Test
    public void ifValidatorExceptionIsThrownIfPartIsBiggerThanMaxImageSize() {
        PictureUploadValidator validator = new PictureUploadValidator();
        validator.setBundle(bundle);
        Part part = mock(Part.class);
        when(part.getContentType()).thenReturn("image/");
        when(part.getSize()).thenReturn(Long.MAX_VALUE);

        exception.expect(javax.faces.validator.ValidatorException.class);

        validator.validate(mockedContext, null, part);
    }

    @Test
    public void ifValidatorExceptionIsThrownIfPartContentTypeIsNotAnImage() {
        PictureUploadValidator validator = new PictureUploadValidator();
        validator.setBundle(bundle);
        Part part = mock(Part.class);
        when(part.getContentType()).thenReturn("text/plain");
        when(part.getSize()).thenReturn(Long.valueOf(0));

        exception.expect(javax.faces.validator.ValidatorException.class);

        validator.validate(mockedContext, null, part);
    }
}
