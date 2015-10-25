package de.schoderer.bookstore.db;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import java.io.Serializable;

/**
 * Created by schod on 25.10.2015.
 */
@Stateless
public class DBProducer implements Serializable {
    //TODO change to real db
    private static final BookPersistence persistence = new MockUpBookPersistence();


    @Produces
    public BookPersistence createProducer() {
        return persistence;
    }
}
