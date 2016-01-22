package de.schoderer.bookstore.db.interfaces.impls;

import de.schoderer.bookstore.db.BasicPersistence;
import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.domain.security.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Created by Michael Schoderer on 22.12.15.
 */
@Stateless
public class UserPersistenceImpl extends BasicPersistence implements UserPersistence {
    private static final Logger LOG = LogManager.getLogger(UserPersistenceImpl.class);

    @Override
    public boolean checkIfEmailIsAlreadyRegistered(String userName) {
        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findUser", User.class);
        query.setParameter("username", userName);
        query.setMaxResults(1);
        //Return false if user exists -> list is NOT empty...
        return !query.getResultList().isEmpty();
    }

    @Override
    public User saveUser(User user) {
        getEntityManager().persist(user);
        LOG.info("Created User: " + user);
        return user;
    }

}
