package de.schoderer.bookstore.domain.book;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by schod on 07.11.2015.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Tag.findLike", query = "SELECT t FROM Tag t WHERE  t.tag LIKE :name"),
        @NamedQuery(name = "Tag.findByName", query = "SELECT t FROM Tag t WHERE  t.tag = :name"),
        @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t")

})
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tag;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Tag{" +
                "tag='" + tag + '\'' +
                '}';
    }
}
