package com.netCracker.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class Customer implements Serializable {
    private Integer id;
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32")
    private String name;
    @Email(message = "Login entered invalid")
    private String login;
    @Size(min = 1, max = 32, message = "Password must be between 1 and 32")
    private String pass;

    public Customer() {
    }

    public Customer(String name, String login, String pass) {
        this.name = name;
        this.login = login;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
