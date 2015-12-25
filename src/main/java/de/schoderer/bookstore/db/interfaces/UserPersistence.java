package de.schoderer.bookstore.db.interfaces;

import de.schoderer.bookstore.domain.security.User;

/**
 * Created by michael on 22.12.15.
 */
public interface UserPersistence {

    /**
     * Find the user with the specific username in the database
     * @param userName
     * @return the user with the username
     */
    User findUserByName(String userName);

    /**
     * Check if username already exists in the database
     * @param userName
     * @return true if exists, else false
     */
    boolean checkIfUserNameIsFree(String userName);
}
