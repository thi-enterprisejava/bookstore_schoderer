package de.schoderer.bookstore.utils;

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
}
