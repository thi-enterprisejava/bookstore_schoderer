package de.schoderer.bookstore.services;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by michael on 31.12.2015.
 */
@ApplicationScoped
public class Configuration implements Serializable {
    private transient Path basePath = Paths.get(System.getProperty("user.home"), "files");

    public Path getBasePath() {
        return basePath;
    }
}
