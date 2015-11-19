package de.schoderer.bookstore.domain;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * Created by schod on 07.11.2015.
 */
@Entity(name = "tag")
public class Tag extends IdentifyableDBObject{
    private String tag;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return Objects.equals(tag, tag1.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
