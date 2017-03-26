package com.netCracker.dao.serialization;

import com.netCracker.dao.CustomerDao;
import com.netCracker.model.Customer;
import com.netCracker.security.MD5Hash;
import com.netCracker.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private final static String FILE_NAME = "ser/Customer.bin";


    @Override
    public boolean create(Customer entity) {
        List<Customer> list = getAll();
        if (list==null){
            list = new ArrayList<>();
        }
        MD5Hash md5Hash = new MD5Hash();
        entity.setPass(md5Hash.MD5(entity.getPass()));
        list.add(entity);
        return SerializationUtils.writeEntity(list,FILE_NAME);
    }

    @Override
    public Customer getById(int id) {
        List<Customer> list = getAll();
        Customer obj = null;
        for (Customer customer : list){
            if (id == customer.getId()){
                obj = customer;
            }
        }
        return obj;
    }

    @Override
    public List<Customer> getAll() {
        return SerializationUtils.readEntity(FILE_NAME);
    }

    @Override
    public boolean update(Customer entity) {
        List<Customer> list = getAll();
        for (Customer customer : list){
            if (entity.getId() == customer.getId()){
                MD5Hash md5 = new MD5Hash();
                customer.setName(entity.getName());
                customer.setLogin(entity.getLogin());
                customer.setPass(md5.isValidMD5(entity.getPass()) ? entity.getPass() : md5.MD5(entity.getPass()));
            }
        }
        return SerializationUtils.writeEntity(list,FILE_NAME);
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
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

}
