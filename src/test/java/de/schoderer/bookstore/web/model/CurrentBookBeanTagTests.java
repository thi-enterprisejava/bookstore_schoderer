package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.domain.book.Tag;
import de.schoderer.bookstore.testUtils.web.model.CurrentBookBeanFactory;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by michael on 01.01.2016.
 */
@Category(BeanTest.class)
public class CurrentBookBeanTagTests {
    @Rule
    public CurrentBookBeanFactory beanFactory = new CurrentBookBeanFactory();


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
        currentBookBean.setTagName(tags);
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
        String[] tags = new String[]{"", null, " "};
        Stream.of(tags).forEach(currentBookBean::doAddTags);
        List<Tag> tagList = currentBookBean.getCurrentBook().getTags();
        Assert.assertFalse(tagList.contains(new Tag("")));
        Assert.assertFalse(tagList.contains(new Tag(null)));
        Assert.assertFalse(tagList.contains(new Tag(" ")));
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


}
