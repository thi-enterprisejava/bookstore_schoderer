package de.schoderer.bookstore.web;

import de.schoderer.bookstore.web.model.PageSwitcherBeanTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by michael on 02.01.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CurrentBookBeanTestSuite.class,
        PageSwitcherBeanTest.class
})
public class BeanTests {
}
