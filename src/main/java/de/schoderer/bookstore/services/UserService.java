package de.schoderer.bookstore.services;

import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.domain.security.User;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by Michael Schoderer on 20.01.2016.
 */
public class UserService implements Serializable {
    @Inject
    private UserPersistence persistence;


    public User saveUser(User newUser) {
        return persistence.saveUser(newUser);
    }

    public boolean checkIfEmailIsAlreadyRegistered(String email) {
        return persistence.checkIfEmailIsAlreadyRegistered(email);
    }
}
