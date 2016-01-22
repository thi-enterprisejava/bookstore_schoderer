package de.schoderer.bookstore.db;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by michael on 22.12.15.
 */
@Stateless
public class BasicPersistence {

    @PersistenceContext(name = "primary")
    private javax.persistence.EntityManager em;


    protected EntityManager getEntityManager() {
        return em;
    }
}
