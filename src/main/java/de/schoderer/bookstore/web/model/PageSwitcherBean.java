package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.utils.Pages;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by michael on 10.11.15.
 */
@Named
@SessionScoped
public class PageSwitcherBean implements Serializable {
    public static final String FACES_REDIRECT = "?faces-redirect=true";
    private static final Logger LOG = Logger.getLogger(PageSwitcherBean.class);
    private Pages lastPage = Pages.INDEX;
    private Pages currentPage;

    /**
     * Returns the address of a Page from Pages
     *
     * @param pageString
     * @return address
     */
    public String switchPage(String pageString) {
        Pages page = Pages.valueOf(pageString);
        return switchPage(page);
    }

    /**
     * Returns the address of a Page from Pages
     *
     * @param page
     * @return address
     */
    public String switchPage(Pages page) {
        lastPage = currentPage;
        currentPage = page;
        if (LOG.isInfoEnabled()) {
            LOG.info("Switched page to: " + page.toString());
        }
        return getFileNameWithRedirect(page);
    }

    /**
     * Jumps one page backwards in the History
     *
     * @return address
     */
    public String backwards() {
        String redirectToLastPage = getFileNameWithRedirect(lastPage);
        switchPage(lastPage);
        return redirectToLastPage;
    }

    /**
     * Adds the redirect extension for jsf to the page filename
     *
     * @return filename with redirect
     */
    protected String getFileNameWithRedirect(Pages page) {
        return page.getFileName().concat(FACES_REDIRECT);
    }

    public Pages getLastPage() {
        return lastPage;
    }

    public void setLastPage(Pages lastPage) {
        this.lastPage = lastPage;
    }

    public Pages getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Pages currentPage) {
        this.currentPage = currentPage;
    }
}
