package de.schoderer.bookstore.model;

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
    private static final Logger LOG = Logger.getLogger(PageSwitcherBean.class);

    private static final String ACTIVE_CSS_CLASS = "active";
    private String addBookPage = "";
    private String indexPage = "active";
    private String listResultsPage = "";

    private Pages lastPage;
    private Pages currentPage;


    public String switchPage(String page) {
        return switchPage(Pages.valueOf(page));
    }

    public String switchPage(Pages page) {
        lastPage = currentPage;
        currentPage = page;
        resetCssClassStrings();
        changeCSS(page);
        if (LOG.isInfoEnabled()) {
            LOG.info("Switched page to: " + page.toString());
        }
        return page.getFileName() + "?faces-redirect=true";
    }

    private void changeCSS(Pages page) {
        switch (page) {
            case INDEX:
                indexPage = ACTIVE_CSS_CLASS;
                break;
            case LIST:
                listResultsPage = ACTIVE_CSS_CLASS;
                break;
            case ADD:
                addBookPage = ACTIVE_CSS_CLASS;
                break;
        }
    }


    private void resetCssClassStrings() {
        addBookPage = "";
        indexPage = "";
        listResultsPage = "";
    }

    public String getAddBookPage() {
        return addBookPage;
    }

    public void setAddBookPage(String addBookPage) {
        this.addBookPage = addBookPage;
    }

    public String getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(String indexPage) {
        this.indexPage = indexPage;
    }

    public String getListResultsPage() {
        return listResultsPage;
    }

    public void setListResultsPage(String listResultsPage) {
        this.listResultsPage = listResultsPage;
    }
}
