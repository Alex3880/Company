package com.netCracker.controllers;

import com.netCracker.comparators.EmployeeIdComparator;
import com.netCracker.dao.EmployeeDao;
import com.netCracker.dao.TaskDao;
import com.netCracker.model.Employee;
import com.netCracker.validators.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private TaskDao taskDao;

    @RequestMapping(value = "addEmployee", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String add(@RequestParam String nameEmp, @RequestParam String loginEmp, @RequestParam String passEmp, @RequestParam String lastNameEmp, @RequestParam int skillId) {
        Employee employee = new Employee(nameEmp, loginEmp, passEmp, lastNameEmp, skillId);
        EntityValidator<Employee> validator = new EntityValidator<>();
        String result = validator.validate(employee);
        if (result.isEmpty()) {
            employeeDao.create(employee);
        }
        return result;
    }

    @RequestMapping(value = "editEmployee", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String edit(@RequestParam String nameEmp, @RequestParam String loginEmp, @RequestParam String passEmp, @RequestParam String lastNameEmp, @RequestParam int skillId, @RequestParam int idEmp) {
        Employee employee = new Employee(nameEmp, loginEmp, passEmp, lastNameEmp, skillId);
        employee.setId(idEmp);
        EntityValidator<Employee> validator = new EntityValidator<>();
        String result = validator.validate(employee);
        if (result.isEmpty()) {
            employeeDao.update(employee);
        }
        return result;
    }

    @RequestMapping(value = "managers", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Employee> showManagers() {
        List<Employee> list = employeeDao.getManagers();
        if (list != null) {
            list.sort(new EmployeeIdComparator());
        }
        return list;
    }

    @RequestMapping(value = "employers", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Employee> showList() {
        List<Employee> list = employeeDao.getAll();
        if (list != null) {
            list.sort(new EmployeeIdComparator());
        }
        return list;
    }

    @RequestMapping(value = "employee", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Employee showEmployee(@RequestParam int id) {
        return employeeDao.getById(id);
    }

    @RequestMapping(value = "deleteEmployee", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String delete(@RequestParam int id) {
        employeeDao.delete(id);
        return "deleted";
    }

    @RequestMapping(value = "employeersByIdSkill", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Employee> employeeByIdSkill(int idTask) {
        List<Employee> employeesByIdSkill = employeeDao.getBySkill(taskDao.getById(idTask).getIdSkill());
        if (employeesByIdSkill.size() == 0) {
            return null;
        } else {

            return employeesByIdSkill;
        }
    }
}
