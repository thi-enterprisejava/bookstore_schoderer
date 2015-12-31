package de.schoderer.bookstore.utils;

import javax.inject.Singleton;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by michael on 31.12.2015.
 */
@Singleton
public class Configuration {
    private Path basePath = Paths.get(System.getProperty("user.home"), "files");

    public Path getBasePath(){
        return basePath;
    }
}
