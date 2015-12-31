package de.schoderer.bookstore.utils;

/**
 * Created by schod on 05.11.2015.
 */
public enum Pages {
    INDEX("index.xhtml"), LIST("listResults.xhtml"), ADD("addBook.xhtml"), DETAILS("showDetails.xhtml"), LOGIN("login.xthml"), REGISTER("registration.xhtml");


    private String fileName;

    Pages(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
