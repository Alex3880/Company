package com.netCracker.controllers;

import com.netCracker.comparators.CustomerIdComparator;
import com.netCracker.dao.CustomerDao;
import com.netCracker.model.Customer;
import com.netCracker.validators.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerDao customerDao;

    @RequestMapping(value = "addCustomer", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String add(@RequestParam String name, @RequestParam String login, @RequestParam String password) {
        Customer customer = new Customer(name, login, password);
        EntityValidator<Customer> validator = new EntityValidator<>();
        String result = validator.validate(customer);
        if (result.isEmpty()) {
            customerDao.create(customer);
        }
        return result;
    }

    @RequestMapping(value = "editCustomer", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String edit(@RequestParam int id, @RequestParam String name, @RequestParam String login, @RequestParam String password) {
        Customer customer = new Customer(name, login, password);
        customer.setId(id);
        EntityValidator<Customer> validator = new EntityValidator<>();
        String result = validator.validate(customer);
        if (result.isEmpty()) {
            customerDao.update(customer);
        }
        return result;
    }

    @RequestMapping(value = "customers", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Customer> showList() {
        List<Customer> list = customerDao.getAll();
        if (list != null) {
            list.sort(new CustomerIdComparator());
        }
        return list;
    }

    @RequestMapping(value = "customer", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Customer show(@RequestParam int id) {
        return customerDao.getById(id);
    }

    @RequestMapping(value = "deleteCus", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String delete(@RequestParam int id) {
        customerDao.delete(id);
        return "deleted";
    }
}
