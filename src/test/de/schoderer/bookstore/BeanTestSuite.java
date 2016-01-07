package de.schoderer.bookstore;

import de.schoderer.bookstore.web.model.BeanTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by michael on 31.12.2015.
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(BeanTest.class)
@Suite.SuiteClasses(AllTests.class)
public class BeanTestSuite {
}
