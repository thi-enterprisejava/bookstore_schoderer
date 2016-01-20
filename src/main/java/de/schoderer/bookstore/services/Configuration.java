package de.schoderer.bookstore.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by michael on 31.12.2015.
 */
@ApplicationScoped
@Named
public class Configuration implements Serializable {
    private transient Path basePath = Paths.get(System.getProperty("user.home"), "files");

    public Path getBasePath() {
        return basePath;
    }

    /**
     * Get the directory where all images are saved
     *
     * @return
     */
    public Path getImagePath() {
        return getBasePath().resolve("images");
    }

    /**
     * Get the directory where all books are saved
     *
     * @return
     */
    public Path getBookPath() {
        return getBasePath().resolve("books");
    }
}
