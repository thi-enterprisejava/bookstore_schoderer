package de.schoderer.bookstore;

import de.schoderer.bookstore.integrationtests.BookServiceIntegrationTest;
import de.schoderer.bookstore.integrationtests.UserServiceIntegrationTest;
import de.schoderer.bookstore.services.BookServiceTest;
import de.schoderer.bookstore.services.UserServiceTest;
import de.schoderer.bookstore.utils.validator.EmailValidatorCorrectEmailsTest;
import de.schoderer.bookstore.utils.validator.EmailValidatorIncorrectEmailsTest;
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
        PageSwitcherBeanTest.class,
        SearchBeanTest.class,
        UserRegistrationBeanTest.class,

        //ValidatorTests
        EmailValidatorTest.class,
        EmailValidatorCorrectEmailsTest.class,
        EmailValidatorIncorrectEmailsTest.class,
        BookUploadValidatorTest.class,
        PictureUploadValidatorTest.class,

        //ServiceTests
        BookServiceTest.class,
        UserServiceTest.class,


        //IntegrationTests (long running...)
        UserServiceIntegrationTest.class,
        BookServiceIntegrationTest.class
})
public class AllTests {
}
