package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.utils.MessageBuilder;
import de.schoderer.bookstore.utils.Pages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by michael on 22.12.15.
 */
@Named
@RequestScoped
public class UserRegistrationBean {
    @Inject
    private UserPersistence persistence;
    @Inject
    private MessageBuilder messageBuilder;
    @Inject
    private PageSwitcherBean switcherBean;



    private String email;
    private String userName;
    private String password;
    private String repeatedRassword;


    public String doRegisterUser(){


        return switcherBean.switchPage(Pages.INDEX);
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
