package de.schoderer.bookstore.utils;

/**
 * Created by schod on 05.11.2015.
 */
public enum Pages {
    SEARCH("index.xhtml"), LIST("listResults.xhtml");


    private String fileName;

    private Pages(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
