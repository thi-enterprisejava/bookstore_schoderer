package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.utils.TestFileRule;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by michael on 31.12.2015.
 */
public class CurrentBookBeanTest {
    private  static CurrentBookBean bean;
    @Rule
    public TestFileRule testFileRule_ = new TestFileRule();

    @BeforeClass
    public static void setUp(){
        bean = new CurrentBookBean();
    }







    @Test
    public void uploadAndSaveFileToHardDiskTest() throws IOException {
        File file = testFileRule_.getTestFile();

        Assert.assertTrue(Files.exists(file.toPath()));
        StringBuilder builder = new StringBuilder();
        Files.lines(file.toPath()).forEach(builder::append);
        System.out.println("Content: "+builder.toString());

    }
}
