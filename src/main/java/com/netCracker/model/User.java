package com.netCracker.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class User {
    private final static String LOGIN_ADMIN = "admin";
    private final static String PASSWORD_ADMIN = "admin";

    @Size(min = 1, message = "You aren't entered an email address")
    private String login;
    @Size(min = 1, message = "You aren't entered a password")
    private String pass;

    public boolean isAdmin() {
        return LOGIN_ADMIN.equals(login) && PASSWORD_ADMIN.equals(pass);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
