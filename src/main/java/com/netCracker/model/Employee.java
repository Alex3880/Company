package com.netCracker.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32")
    private String name;
    @Email(message = "Login entered invalid")
    private String login;
    @Size(min = 1, max = 32, message = "Password must be between 1 and 32")
    private String pass;
    @Size(min = 1, max = 32, message = "Last name must be between 1 and 32")
    private String lastName;
    private int idSkill;

    public Employee() {
    }

    public Employee(String name, String login, String pass, String lastName, int idSkill) {
        this.name = name;
        this.login = login;
        this.pass = pass;
        this.lastName = lastName;
        this.idSkill = idSkill;
    }

    public boolean isManager(){
        return idSkill==4;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", lastName='" + lastName + '\'' +
                ", idSkill=" + idSkill +
                '}';
    }
}
