package com.netCracker.dao;

import com.netCracker.model.Employee;
import com.netCracker.model.Project;
import com.netCracker.model.User;
import com.netCracker.security.MD5Hash;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.stream.Collectors;

public interface EmployeeDao extends CRUDable<Employee> {
    boolean delete(int id);

    default List<Employee> getManagers() {
        List<Employee> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdSkill() != 4) {
                list.remove(i);
            }
        }
        return list;
    }

    default Employee getByLogin(String login) {
        List<Employee> employees = getAll();
        Employee employee = null;
        for (Employee obj : employees) {
            if (obj.getLogin().equalsIgnoreCase(login)) {
                employee = obj;
            }
        }
        return employee;
    }

    ;

    default List<Employee> getBySkill(int idSkill) {
        return getAll().stream().filter(task -> task.getIdSkill() == idSkill).collect(Collectors.toList());
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
