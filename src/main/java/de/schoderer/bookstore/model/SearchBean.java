package de.schoderer.bookstore.model;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.book.Book;
import de.schoderer.bookstore.utils.Pages;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by schod on 05.11.2015.
 */
@Named
@SessionScoped
public class SearchBean implements Serializable {
    @Inject
    private ActivePageBean navBean;

    @Inject
    private BookPersistence persistence;

    private String search;
    private List<Book> searchResults;

    public String fetchAllBooks() {
        searchResults = persistence.fetchAllBooks();
        return navBean.switchPage(Pages.LIST);
    }

    public String doSearch() {
        searchResults = persistence.fetchAllBooksByTitle(search);
        return navBean.switchPage(Pages.LIST);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Book> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Book> searchResults) {
        this.searchResults = searchResults;
    }
}
