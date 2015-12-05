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

    private String imageLocation;
    private String fileLocation;

    public DataFileLocation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataFileLocation that = (DataFileLocation) o;
        return Objects.equals(imageLocation, that.imageLocation) &&
                Objects.equals(fileLocation, that.fileLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageLocation, fileLocation);
    }

    @Override
    public String toString() {
        return "DataFileLocation{" +
                "id=" + id +
                ", imageLocation='" + imageLocation + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                '}';
    }
}
