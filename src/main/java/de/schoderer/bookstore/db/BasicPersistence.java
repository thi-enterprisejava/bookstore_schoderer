package de.schoderer.bookstore.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by michael on 22.12.15.
 */
public class BasicPersistence {

    @PersistenceContext
    private javax.persistence.EntityManager em;


    protected EntityManager getEntityManager() {
        return em;
    }
}
