package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.domain.security.User;
import de.schoderer.bookstore.domain.security.UserRole;
import de.schoderer.bookstore.utils.ExternalComponents;
import de.schoderer.bookstore.utils.Pages;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by Michael Schoderer on 22.12.15.
 */
@Named
@RequestScoped
public class UserRegistrationBean {
    private static final Logger LOGGER = LogManager.getLogger(UserRegistrationBean.class);
    @Inject
    private UserPersistence persistence;
    @Inject
    private ExternalComponents externalComponents;
    @Inject
    private PageSwitcherBean switcherBean;


    private String email;
    private String password;
    private User newUser;

    protected String hash256(String data) throws NoSuchAlgorithmException {
        if (data == null || "".equals(data))
            return null;
        return Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-256").digest(data.getBytes()));
    }

    public String doRegisterUser() throws NoSuchAlgorithmException {
        if (checkIfEmailAlreadyInDatabase()) {
            newUser = new User(email, hash256(password));
            newUser.getUserRoles().add(new UserRole("user"));
            persistence.saveUser(newUser);
            return switcherBean.switchPage(Pages.LOGIN);
        } else {
            return externalComponents.getCurrentPage();
        }
    }

    /**
     * Check if email already exists, other test a not neccassary, because other parameters are checked via validators on the serverside
     *
     * @return true if email not already exists, else false
     */
    protected boolean checkIfEmailAlreadyInDatabase() {
        if (email == null || persistence.checkIfEmailIsAlreadyRegistered(email)) {
            externalComponents.sendMessage(externalComponents.getResourceBundleStringInCurrentLocal("error.emailAlreadyRegistered"));
            LOGGER.debug("Username already taken: " + email);
            return false;
        }
        return true;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public UserPersistence getPersistence() {
        return persistence;
    }

    public void setPersistence(UserPersistence persistence) {
        this.persistence = persistence;
    }

    public ExternalComponents getExternalComponents() {
        return externalComponents;
    }

    public void setExternalComponents(ExternalComponents externalComponents) {
        this.externalComponents = externalComponents;
    }

    public PageSwitcherBean getSwitcherBean() {
        return switcherBean;
    }

    public void setSwitcherBean(PageSwitcherBean switcherBean) {
        this.switcherBean = switcherBean;
    }
}
