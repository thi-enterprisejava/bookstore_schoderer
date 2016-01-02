package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.utils.Pages;
import de.schoderer.bookstore.web.BeanTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Created by michael on 02.01.2016.
 */
@Category(BeanTest.class)
public class PageSwitcherBeanTest {

    @Test
    public void testIfLastPageIsSaved() {
        PageSwitcherBean bean = new PageSwitcherBean();
        bean.switchPage(Pages.ADD);
        bean.switchPage(Pages.LOGIN);
        Assert.assertEquals(Pages.ADD, bean.getLastPage());
    }

    @Test
    public void testIfCurrentPageIsSaved() {
        PageSwitcherBean bean = new PageSwitcherBean();
        bean.switchPage(Pages.ADD);
        bean.switchPage(Pages.LOGIN);
        Assert.assertEquals(Pages.LOGIN, bean.getCurrentPage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfExceptionIfPageIsUnknown() {
        PageSwitcherBean bean = new PageSwitcherBean();
        bean.switchPage("hallo");
    }

    @Test
    public void testIfPageSwitchWithStringWorks() {
        PageSwitcherBean bean = new PageSwitcherBean();
        bean.switchPage("INDEX");
        Assert.assertEquals(Pages.INDEX, bean.getCurrentPage());
    }

    @Test
    public void testIfBackwardsWorksCorrectly() {
        PageSwitcherBean bean = new PageSwitcherBean();
        Pages firstPage = Pages.ADD;
        bean.switchPage(firstPage);
        Pages secoundPage = Pages.LOGIN;
        bean.switchPage(secoundPage);
        Assert.assertTrue(bean.backwards().startsWith(firstPage.getFileName()));
        Assert.assertEquals(firstPage, bean.getCurrentPage());
        Assert.assertEquals(secoundPage, bean.getLastPage());
    }
}
