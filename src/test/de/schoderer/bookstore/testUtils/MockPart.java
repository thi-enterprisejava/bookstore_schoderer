package de.schoderer.bookstore.testUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collection;

/**
 * Created by michael on 31.12.2015.
 */
public class MockPart implements Part {
    private File testfile;
    private String contentType;

    public MockPart(File file, String contentType) {
        this.testfile = file;
        this.contentType = contentType;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(testfile);
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getName() {
        return testfile.getName();
    }

    @Override
    public String getSubmittedFileName() {
        return testfile.getName();
    }

    @Override
    public long getSize() {
        return testfile.getTotalSpace();
    }

    @Override
    public void write(String fileName) throws IOException {
        throw new UnsupportedOperationException("Writing to file is not supported");
    }

    @Override
    public void delete() throws IOException {
        Files.deleteIfExists(testfile.toPath());
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }
}
