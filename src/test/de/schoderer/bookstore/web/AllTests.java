package de.schoderer.bookstore.web;

import de.schoderer.bookstore.web.model.CurrentBookBeanDeleteTest;
import de.schoderer.bookstore.web.model.CurrentBookBeanTagTests;
import de.schoderer.bookstore.web.model.CurrentBookBeanUploadTest;
import de.schoderer.bookstore.web.model.PageSwitcherBeanTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by michael on 02.01.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CurrentBookBeanTagTests.class,
        CurrentBookBeanDeleteTest.class,
        CurrentBookBeanUploadTest.class,
        PageSwitcherBeanTest.class
})
public class AllTests {
}
