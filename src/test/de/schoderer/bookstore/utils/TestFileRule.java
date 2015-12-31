package de.schoderer.bookstore.utils;

import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by michael on 31.12.2015.
 */
public class TestFileRule extends TemporaryFolder {
    private File testFile;

    @Override
    protected void before() throws Throwable {
        super.before();
        testFile = newFile("TestFile.txt");
        Files.write(testFile.toPath(), "Hallo Hier ist die TestDatei".getBytes());
    }

    public File getTestFile(){
        return testFile;
    }

}
