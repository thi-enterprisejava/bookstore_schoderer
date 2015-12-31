package de.schoderer.bookstore.db.interfaces.impls;

import de.schoderer.bookstore.db.BasicPersistence;
import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.domain.security.User;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;

/**
 * Created by michael on 22.12.15.
 */
@Named
@Stateless
public class UserPersistenceImpl extends BasicPersistence implements UserPersistence {
    @Override
    public User findUserByName(String userName) {
        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findUser", User.class);
        query.setParameter("username", userName);
        return query.getSingleResult();
    }

    @Override
    public boolean checkIfEmailIsAlreadyRegistered(String userName) {
        TypedQuery<Long> query = getEntityManager().createNamedQuery("User.userNameExists", Long.class)
                .setParameter("username", userName)
                .setMaxResults(1);
        return query.getResultList().isEmpty();
    }

    @Override
    public User saveUser(User user) {
        getEntityManager().persist(user);
        return user;
    }

}
