package de.schoderer.bookstore.db.interfaces;

import de.schoderer.bookstore.domain.security.User;
import de.schoderer.bookstore.utils.interceptor.TimeLogging;

import javax.annotation.security.PermitAll;

/**
 * Created by Michael Schoderer on 22.12.15.
 */
@TimeLogging
public interface UserPersistence {

    /**
     * Find the user with the specific username in the database
     *
     * @param userName
     * @return the user with the username
     */
    @PermitAll
    User findUserByName(String userName);

    /**
     * Check if username already exists in the database
     *
     * @param userName
     * @return true if exists, else false
     */
    @PermitAll
    boolean checkIfEmailIsAlreadyRegistered(String userName);

    @PermitAll
    User saveUser(User user);
}
