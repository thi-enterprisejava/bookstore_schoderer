package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.domain.security.User;
import de.schoderer.bookstore.domain.security.UserRole;
import de.schoderer.bookstore.services.UserService;
import de.schoderer.bookstore.utils.ExternalComponents;
import de.schoderer.bookstore.utils.Pages;
import org.jboss.security.auth.spi.Util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Michael Schoderer on 22.12.15.
 */
@Named
@RequestScoped
public class UserRegistrationBean implements Serializable {
    @Inject
    private UserService service;
    @Inject
    private ExternalComponents externalComponents;
    @Inject
    private PageSwitcherBean switcherBean;


    private String email;
    private String password;
    private User newUser;

    /**
     * Hashes the given String to SHA-256, BASE64, UTF-8 (SHA-512 has currently problems with wildfly 9)
     * => https://issues.jboss.org/browse/WFLY-4298
     *
     * @param password
     * @return hashedString
     * @throws NoSuchAlgorithmException
     */
    protected String hash256(String password) throws NoSuchAlgorithmException {
        if (password == null || "".equals(password))
            return null;
        return Util.createPasswordHash("SHA-256", "BASE64", "UTF-8", null, password);

    }

    public String doRegisterUser() throws NoSuchAlgorithmException {
        newUser = new User(email, hash256(password));
        UserRole userRole = new UserRole("user");
        userRole.setUser(newUser);
        newUser.getUserRoles().add(userRole);
        service.saveUser(newUser);
        return switcherBean.switchPage(Pages.LOGIN);
    }

    public String logout() {
        externalComponents.invalidateSession();
        return switcherBean.switchPage(Pages.INDEX);
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

    public UserService getService() {
        return service;
    }

    public void setService(UserService service) {
        this.service = service;
    }
}
