package de.schoderer.bookstore.testUtils;

import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by michael on 31.12.2015.
 */
public class TestFileRule extends TemporaryFolder {
    private static AtomicInteger counter = new AtomicInteger();

    @Override
    protected void before() throws Throwable {
        super.before();
    }

    public File getRandomTestFile() throws IOException {
        String fileName = "TestFile" + counter.getAndIncrement();
        if (counter.get() % 2 == 0) {
            fileName = fileName.concat(".txt");
        }
        File testFile = newFile(fileName);
        String content = "Hallo TestFile" + UUID.randomUUID().toString();
        Files.write(testFile.toPath(), content.getBytes());
        return testFile;
    }

}
