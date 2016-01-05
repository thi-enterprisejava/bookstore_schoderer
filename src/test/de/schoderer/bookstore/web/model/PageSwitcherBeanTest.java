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
    public void ifLastPageIsSaved() {
        PageSwitcherBean bean = new PageSwitcherBean();
        bean.switchPage(Pages.ADD);
        bean.switchPage(Pages.LOGIN);
        Assert.assertEquals(Pages.ADD, bean.getLastPage());
    }

    @Test
    public void ifCurrentPageIsSaved() {
        PageSwitcherBean bean = new PageSwitcherBean();
        bean.switchPage(Pages.ADD);
        bean.switchPage(Pages.LOGIN);
        Assert.assertEquals(Pages.LOGIN, bean.getCurrentPage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifExceptionIsThrownIfPageIsUnknown() {
        PageSwitcherBean bean = new PageSwitcherBean();
        bean.switchPage("hallo");
    }

    @Test
    public void ifPageSwitchWithStringWorks() {
        PageSwitcherBean bean = new PageSwitcherBean();
        bean.switchPage("INDEX");
        Assert.assertEquals(Pages.INDEX, bean.getCurrentPage());
    }

    @Test
    public void ifBackwardsWorksCorrectly() {
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
