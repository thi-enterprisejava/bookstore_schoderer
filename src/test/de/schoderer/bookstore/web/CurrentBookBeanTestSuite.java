package de.schoderer.bookstore.web;

import de.schoderer.bookstore.web.model.CurrentBookBeanDeleteTest;
import de.schoderer.bookstore.web.model.CurrentBookBeanSaveTests;
import de.schoderer.bookstore.web.model.CurrentBookBeanUploadTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by michael on 31.12.2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CurrentBookBeanUploadTest.class,
        CurrentBookBeanDeleteTest.class,
        CurrentBookBeanSaveTests.class


})
public class CurrentBookBeanTestSuite {
}
