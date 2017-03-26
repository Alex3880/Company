package com.netCracker.dao.jsonFile;

import com.netCracker.dao.CustomerDao;
import com.netCracker.model.Customer;
import com.netCracker.security.MD5Hash;
import com.netCracker.utils.JsonFileUtils;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private final static String FILE_NAME = "json/Customer.txt";

    @Override
    public boolean create(Customer entity) {
        MD5Hash md5Hash = new MD5Hash();
        entity.setPass(md5Hash.MD5(entity.getPass()));
        return JsonFileUtils.writeEntity(entity, FILE_NAME);
    }

    @Override
    public Customer getById(int id) {
        List<Customer> list = getAll();
        Customer cus = null;
        for (Customer customer : list) {
            if (id == customer.getId()) {
                cus = customer;
            }
        }
        return cus;
    }

    @Override
    public List<Customer> getAll() {
        return (List) JsonFileUtils.getAllEntitys(FILE_NAME, Customer.class);
    }

    @Override
    public boolean update(Customer entity) {
        List<Customer> list = getAll();
        for (Customer customer : list) {
            if (entity.getId() == customer.getId()) {
                MD5Hash md5 = new MD5Hash();
                customer.setName(entity.getName());
                customer.setLogin(entity.getLogin());
                customer.setPass(md5.isValidMD5(entity.getPass()) ? entity.getPass() : md5.MD5(entity.getPass()));
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

    @Override
    public boolean delete(Customer entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        List<Customer> list = getAll();
        for (int i=0;i<list.size();i++) {
            if (id == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

}
