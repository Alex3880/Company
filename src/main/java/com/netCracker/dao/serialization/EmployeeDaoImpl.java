package com.netCracker.dao.serialization;

import com.netCracker.dao.EmployeeDao;
import com.netCracker.model.Employee;
import com.netCracker.model.User;
import com.netCracker.security.MD5Hash;
import com.netCracker.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDaoImpl implements EmployeeDao {
    private final static String FILE_NAME = "ser/Employee.bin";

    @Override
    public boolean create(Employee entity) {
        List<Employee> list = getAll();
        if (list==null){
            list = new ArrayList<>();
        }
        MD5Hash md5Hash = new MD5Hash();
        entity.setPass(md5Hash.MD5(entity.getPass()));
        list.add(entity);
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public Employee getById(int id) {
        List<Employee> list = getAll();
        Employee obj = null;
        for (Employee employee : list) {
            if (id == employee.getId()) {
                obj = employee;
            }
        }
        return obj;
    }

    @Override
    public List<Employee> getAll() {
        return SerializationUtils.readEntity(FILE_NAME);
    }

    @Override
    public boolean update(Employee entity) {
        List<Employee> list = getAll();
        for (Employee employee : list) {
            if (entity.getId() == employee.getId()) {
                employee.setName(entity.getName());
                employee.setLastName(entity.getLastName());
                employee.setIdSkill(entity.getIdSkill());
                employee.setLogin(entity.getLogin());
                MD5Hash md5Hash = new MD5Hash();
                employee.setPass(md5Hash.isValidMD5(entity.getPass()) ? entity.getPass() : md5Hash.MD5(entity.getPass()));
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public boolean delete(Employee entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        List<Employee> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }


}
