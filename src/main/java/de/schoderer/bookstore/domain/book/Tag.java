package de.schoderer.bookstore.domain.book;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by schod on 07.11.2015.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Tag.findLike", query = "SELECT t FROM Tag t WHERE  t.tagName LIKE :name"),
        @NamedQuery(name = "Tag.findByName", query = "SELECT t FROM Tag t WHERE  t.tagName = :name"),
        @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t")

})
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tagName;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return Objects.equals(tagName, tag1.tagName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagName='" + tagName + '\'' +
                '}';
    }
}
