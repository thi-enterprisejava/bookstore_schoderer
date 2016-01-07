package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.domain.book.DataFileLocation;
import de.schoderer.bookstore.testUtils.MockPart;
import de.schoderer.bookstore.testUtils.TestFileRule;
import de.schoderer.bookstore.utils.Configuration;
import de.schoderer.bookstore.web.BeanTest;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by michael on 31.12.2015.
 */
@Category(BeanTest.class)
public class CurrentBookBeanUploadTest {
    private static CurrentBookBean bean;
    @Rule
    public TestFileRule testFileRule_ = new TestFileRule();

    @BeforeClass
    public static void setUp() {
        bean = new CurrentBookBean();
    }

    @Test
    public void ifUploadAndSaveFileReturnsRightDataFileLocation() throws IOException {
        bean.doSetCurrentBook();
        Configuration configuration = Mockito.mock(Configuration.class);
        bean.setConfiguration(configuration);
        Mockito.when(configuration.getBasePath()).thenReturn(testFileRule_.getRoot().toPath());
        File bookFile = testFileRule_.getRandomTestFile();
        bean.setBookFile(new MockPart(bookFile, "text/plain"));
        File imageFile = testFileRule_.getRandomTestFile();
        bean.setImageFile(new MockPart(imageFile, "text/plain"));
        DataFileLocation location = bean.uploadAndSaveFiles();
        Assert.assertNotNull(location);
        Assert.assertTrue(Files.exists(Paths.get(location.getFullImagePath())));
        Assert.assertTrue(Files.exists(Paths.get(location.getFullFilePath())));
        Assert.assertTrue(FileUtils.contentEquals(bookFile, new File(location.getFullFilePath())));
        Assert.assertTrue(FileUtils.contentEquals(imageFile, new File(location.getFullImagePath())));
    }


    @Test
    public void ifNullIsReturnedWhenPartIsNull() throws IOException {
        mockUploadDirecotry();
        Path uploadedFilePath = bean.uploadAndSaveFileToHardDisk(null, true);
        Assert.assertNull(uploadedFilePath);
    }

    @Test
    public void ifBookCanBeUploadedToHardDrive() throws IOException {
        mockUploadDirecotry();
        File testFile = testFileRule_.getRandomTestFile();
        Path uploadedFilePath = bean.uploadAndSaveFileToHardDisk(new MockPart(testFile, "text/plain"), true);
        Assert.assertTrue(Files.exists(uploadedFilePath));
        Assert.assertTrue(FileUtils.contentEquals(testFile, uploadedFilePath.toFile()));

    }

    @Test
    public void ifImageCanBeUploadedToHardDrive() throws IOException {
        mockUploadDirecotry();
        File testFile = testFileRule_.getRandomTestFile();
        Path uploadedFilePath = bean.uploadAndSaveFileToHardDisk(new MockPart(testFile, "text/plain"), false);
        Assert.assertTrue(Files.exists(uploadedFilePath));
    }

    private void mockUploadDirecotry() throws IOException {
        Configuration configuration = Mockito.mock(Configuration.class);
        Path path = testFileRule_.newFolder().toPath();
        Files.createDirectory(path.resolve("books"));
        Files.createDirectory(path.resolve("images"));
        Mockito.when(configuration.getBasePath()).thenReturn(path);
        bean.setConfiguration(configuration);
    }


}
