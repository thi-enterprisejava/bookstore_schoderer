package de.schoderer.bookstore;

import de.schoderer.bookstore.services.IntegrationTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Michael Schoderer on 21.01.2016.
 */
@RunWith(Categories.class)
@Categories.ExcludeCategory(IntegrationTest.class)
@Suite.SuiteClasses(AllTests.class)
public class UnitTestSuite {
}
