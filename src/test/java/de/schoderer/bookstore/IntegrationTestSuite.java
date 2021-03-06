package de.schoderer.bookstore;

import de.schoderer.bookstore.integrationtests.IntegrationTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Michael Schoderer on 21.01.2016.
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(IntegrationTest.class)
@Suite.SuiteClasses(AllTests.class)
public class IntegrationTestSuite {
}
