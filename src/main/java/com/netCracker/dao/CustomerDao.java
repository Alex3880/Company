package com.netCracker.dao;

import com.netCracker.model.Customer;
import com.netCracker.model.User;
import com.netCracker.security.MD5Hash;

import java.util.List;

public interface CustomerDao extends CRUDable<Customer> {
    boolean delete(int id);

    default Customer getByLogin(String login) {
        List<Customer> customers = getAll();
        Customer customer = null;
        for (Customer obj : customers) {
            if (obj.getLogin().equalsIgnoreCase(login)) {
                customer = obj;
            }
        }
        return customer;
    }

    default boolean isExist(User user) {
        if (getByLogin(user.getLogin()) == null) {
            return false;
        } else {
            MD5Hash md5 = new MD5Hash();
            String userPassword = md5.MD5(user.getPass());
            String expectedPassword = getByLogin(user.getLogin()).getPass();
            return userPassword.equals(expectedPassword);
        }
    }
}
