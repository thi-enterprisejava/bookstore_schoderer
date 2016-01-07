package de.schoderer.bookstore;

import de.schoderer.bookstore.utils.validator.ValidatorTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Michael Schoderer on 07.01.2016.
 */

@RunWith(Categories.class)
@Categories.IncludeCategory(ValidatorTest.class)
@Suite.SuiteClasses(AllTests.class)
public class ValidatorTestSuite {
}
