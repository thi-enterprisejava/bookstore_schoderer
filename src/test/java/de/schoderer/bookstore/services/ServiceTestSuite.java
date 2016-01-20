package de.schoderer.bookstore.services;

import de.schoderer.bookstore.AllTests;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Michael Schoderer on 20.01.2016.
 */

@RunWith(Categories.class)
@Categories.IncludeCategory(ServiceTest.class)
@Suite.SuiteClasses(AllTests.class)
public class ServiceTestSuite {
}
