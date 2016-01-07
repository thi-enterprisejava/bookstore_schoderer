package de.schoderer.bookstore;

import de.schoderer.bookstore.utils.validator.EmailValidatorTest;
import de.schoderer.bookstore.utils.validator.upload.BookUploadValidatorTest;
import de.schoderer.bookstore.utils.validator.upload.PictureUploadValidatorTest;
import de.schoderer.bookstore.web.model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by michael on 02.01.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        //BeanTests
        CurrentBookBeanSaveTest.class,
        CurrentBookBeanTagTests.class,
        CurrentBookBeanDeleteTest.class,
        CurrentBookBeanUploadTest.class,
        PageSwitcherBeanTest.class,
        SearchBeanTest.class,

        //ValidatorTests
        EmailValidatorTest.class,
        BookUploadValidatorTest.class,
        PictureUploadValidatorTest.class
})
public class AllTests {
}
