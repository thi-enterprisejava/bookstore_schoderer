package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.testUtils.BookFixture;
import de.schoderer.bookstore.testUtils.CurrentBookBeanFactory;
import de.schoderer.bookstore.web.BeanTest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by michael on 01.01.2016.
 */
@Category(BeanTest.class)
public class CurrentBookBeanTagTests {
    @Rule
    public CurrentBookBeanFactory beanFactory = new CurrentBookBeanFactory();
    @Rule
    public BookFixture bookFixture = new BookFixture();


    @Test
    public void ifTagsCanBeAddedToABook() throws IOException {
        CurrentBookBean currentBookBean = beanFactory.createCurrentBookMock();
        currentBookBean.doSetCurrentBook();
        String[] tags = new String[]{"Test", "ABC", "Book"};
        Stream.of(tags).forEach(currentBookBean::doAddTags);
        List<Tag> tagList = currentBookBean.getCurrentBook().getTags();
        for (String tag : tags) {
            Assert.assertTrue("Tag was not in the list: " + tag, tagList.contains(new Tag(tag.toUpperCase())));
        }
    }

    @Test
    public void ifTagsCanBeAddedToABookAsCSV() throws IOException {
        CurrentBookBean currentBookBean = beanFactory.createCurrentBookMock();
        currentBookBean.doSetCurrentBook();
        String tags = "Test, ABC, Book";
        currentBookBean.setTag(tags);
        currentBookBean.doAddTags();
        List<Tag> tagList = currentBookBean.getCurrentBook().getTags();
        for (String tag : tags.split(",")) {
            Assert.assertTrue("Tag was not in the list: " + tag, tagList.contains(new Tag(tag.toUpperCase().trim())));
        }
    }

    @Test
    public void ifTagsAreNotAddedIfTagIsEmptyOrNull() throws IOException {
        CurrentBookBean currentBookBean = beanFactory.createCurrentBookMock();
        currentBookBean.doSetCurrentBook();
        String[] tags = new String[]{"", null};
        Stream.of(tags).forEach(currentBookBean::doAddTags);
        List<Tag> tagList = currentBookBean.getCurrentBook().getTags();
        Assert.assertFalse(tagList.contains(new Tag("")));
        Assert.assertFalse(tagList.contains(new Tag(null)));
    }

    @Test
    public void ifTagsCanBeRemovedFromBook() throws IOException {
        CurrentBookBean currentBookBean = beanFactory.createCurrentBookMock();
        currentBookBean.doSetCurrentBook();
        String tags = "Book";
        Stream.of(tags).forEach(currentBookBean::doAddTags);
        List<Tag> tagList = currentBookBean.getCurrentBook().getTags();
        Assert.assertTrue("Tag was not in the list: " + tags, tagList.contains(new Tag(tags.toUpperCase().trim())));
        currentBookBean.removeTag(tags);
        Assert.assertFalse("Tag was in the list: " + tags, tagList.contains(new Tag(tags.toUpperCase().trim())));

    }

    @Test
    public void ifTagsAreSavedIfNotAlreadInDatabase() throws IOException {
        CurrentBookBean bean = beanFactory.createCurrentBookMock();
        Mockito.when(bean.getPersistence().fetchAllTags()).thenReturn(tagList);
        Book book = bookFixture.createBookInstance();
        bean.setCurrentBook(book);
        book.getTags().clear();
        Tag tag = new Tag("BOOK");
        book.getTags().add(tag);
        bean.persistTagsIfNotAlreadyInDatabase(book);
        Mockito.verify(bean.getPersistence(), Mockito.times(1)).saveTag(tag);
    }
    @Test
    public void ifTagsAreNotSavedIfAlreadInDatabase() throws IOException {
        CurrentBookBean bean = beanFactory.createCurrentBookMock();
        Mockito.when(bean.getPersistence().fetchAllTags()).thenReturn(tagList);
        Book book = bookFixture.createBookInstance();
        bean.setCurrentBook(book);
        book.getTags().clear();
        Tag tag = new Tag("TEST");
        book.getTags().add(tag);
        bean.persistTagsIfNotAlreadyInDatabase(book);
        Mockito.verify(bean.getPersistence(), Mockito.times(0)).saveTag(tag);
    }

    public static List<Tag> tagList = Arrays.asList(new Tag("TEST"), new Tag("TAG"), new Tag("JUNIT"));

}
