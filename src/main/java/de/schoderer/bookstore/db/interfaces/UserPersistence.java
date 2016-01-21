package de.schoderer.bookstore.db.interfaces;

import de.schoderer.bookstore.domain.security.User;

import javax.annotation.security.PermitAll;
import java.io.Serializable;

/**
 * Created by Michael Schoderer on 22.12.15.
 */
public interface UserPersistence extends Serializable {
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
