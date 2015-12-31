package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.domain.security.User;
import de.schoderer.bookstore.domain.security.UserRole;
import de.schoderer.bookstore.utils.JSFUtils;
import de.schoderer.bookstore.utils.Pages;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by michael on 22.12.15.
 */
@Named
@RequestScoped
public class UserRegistrationBean {
    private static final Logger LOGGER = LogManager.getLogger(UserRegistrationBean.class);
    @Inject
    private UserPersistence persistence;
    @Inject
    private JSFUtils JSFUtils;
    @Inject
    private PageSwitcherBean switcherBean;


    private String email;
    private String userName;
    private String password;
    private String repeatedRassword;

    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public String doRegisterUser() throws NoSuchAlgorithmException {
        if (checkIfInputIsValid()) {
            User newUser = new User(email, hash256(password));
            newUser.getUserRoles().add(new UserRole("user"));
            persistence.saveUser(newUser);
            return switcherBean.switchPage(Pages.INDEX);
        } else {
            return FacesContext.getCurrentInstance().getViewRoot().getViewId();
        }
    }

    private boolean checkIfInputIsValid() {
        if (repeatedRassword == null || !repeatedRassword.equals(password)) {
            JSFUtils.sendMessage("Passwords did not match");
            LOGGER.info("Passwords didnt match");
            return false;
        }
        if (userName == null || persistence.checkIfUserNameIsFree(userName)) {
            JSFUtils.sendMessage("Username already taken");
            LOGGER.info("Username already taken: " + userName);
            return false;
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedRassword() {
        return repeatedRassword;
    }

    public void setRepeatedRassword(String repeatedRassword) {
        this.repeatedRassword = repeatedRassword;
    }
}
