package de.schoderer.bookstore.web;

import de.schoderer.bookstore.web.model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by michael on 02.01.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CurrentBookBeanSaveTest.class,
        CurrentBookBeanTagTests.class,
        CurrentBookBeanDeleteTest.class,
        CurrentBookBeanUploadTest.class,
        PageSwitcherBeanTest.class,
        SearchBeanTest.class
})
public class AllTests {
}
