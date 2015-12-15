package de.schoderer.bookstore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Created by schod on 19.11.2015.
 */
@Entity
public class DataFileLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String imageName;
    private String fileName;
    private String fullImagePath;
    private String fullFilePath;

    public DataFileLocation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullImagePath() {
        return fullImagePath;
    }

    public void setFullImagePath(String fullImagePath) {
        this.fullImagePath = fullImagePath;
    }

    public String getFullFilePath() {
        return fullFilePath;
    }

    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataFileLocation that = (DataFileLocation) o;
        return Objects.equals(imageName, that.imageName) &&
                Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageName, fileName);
    }

    @Override
    public String toString() {
        return "DataFileLocation{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
