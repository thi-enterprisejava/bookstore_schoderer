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
    private JSFUtils jsfUtils;
    @Inject
    private PageSwitcherBean switcherBean;


    private String email;
    private String password;
    private String repeatedRassword;

    //TODO not working....
    private static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] bytes) {
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
        if (email == null || persistence.checkIfEmailIsAlreadyRegistered(email)) {
            //TODO maybe offer paswword reset, if not to much work Work with resourcebundle!!
            jsfUtils.sendMessage("Email already exists");
            LOGGER.info("Username already taken: " + email);
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

    public UserPersistence getPersistence() {
        return persistence;
    }

    public void setPersistence(UserPersistence persistence) {
        this.persistence = persistence;
    }

    public JSFUtils getJsfUtils() {
        return jsfUtils;
    }

    public void setJsfUtils(de.schoderer.bookstore.utils.JSFUtils jsfUtils) {
        this.jsfUtils = jsfUtils;
    }

    public PageSwitcherBean getSwitcherBean() {
        return switcherBean;
    }

    public void setSwitcherBean(PageSwitcherBean switcherBean) {
        this.switcherBean = switcherBean;
    }
}
