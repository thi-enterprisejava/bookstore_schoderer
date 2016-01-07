package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.db.interfaces.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.testUtils.web.model.BookFixture;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;

/**
 * Created by michael on 05.01.2016.
 */
@Category(BeanTest.class)
public class SearchBeanTest {
    @Rule
    public BookFixture fixture = new BookFixture();

    @Test
    public void ifFetchAllResultsWorks() throws IOException {
        List<Book> randomBookList = fixture.createBookList(5);
        SearchBean bean = createSearchBeanWithMockedDependencies();
        Mockito.when(bean.getPersistence().fetchAllBooks()).thenReturn(randomBookList);
        bean.fetchAllBooks();
        Assert.assertThat(bean.getSearchResults(), is(randomBookList));
    }

    @Test
    public void ifSearchForStringWorks() throws IOException {
        String searchString = "Hallo";
        List<Book> randomBookList = fixture.createBookList(5);
        SearchBean bean = createSearchBeanWithMockedDependencies();
        Mockito.when(bean.getPersistence().fetchAllBooksByTitle(searchString)).
                thenReturn(randomBookList.stream().filter(book ->
                        book.getTitle().toUpperCase().contains(searchString.toUpperCase()))
                        .collect(Collectors.toList())
                );
        bean.setSearch(searchString);
        bean.doSearch();
        Assert.assertThat(bean.getSearchResults(), is(randomBookList));
    }


    private SearchBean createSearchBeanWithMockedDependencies() {
        SearchBean bean = new SearchBean();
        BookPersistence persistence = Mockito.mock(BookPersistence.class);
        bean.setPersistence(persistence);
        bean.setNavBean(Mockito.mock(PageSwitcherBean.class));
        return bean;
    }
}
