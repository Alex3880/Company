package com.netCracker.controllers;

import com.netCracker.dao.CustomerDao;
import com.netCracker.dao.EmployeeDao;
import com.netCracker.model.User;
import com.netCracker.security.MD5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@SessionAttributes({"userRole", "name", "id"})
public class AuthorizationController {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private EmployeeDao employeeDao;


    @RequestMapping(value = "check-user", method = RequestMethod.POST)
    public String checkUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, SessionStatus session) {
        if (bindingResult.hasErrors()) {
            session.setComplete();
            return "login";
        }

        MD5Hash md5 = new MD5Hash();
        if (user.isAdmin()) {
            model.addAttribute("userRole", "admin");
            return "forward:adminPanel";

        } else if (customerDao.isExist(user)) {
            model.addAttribute("name", customerDao.getByLogin(user.getLogin()).getName());
            model.addAttribute("id", customerDao.getByLogin(user.getLogin()).getId());
            model.addAttribute("userRole", "customer");
            return "forward:customerPanel";

        } else if (employeeDao.isExist(user) && employeeDao.getByLogin(user.getLogin()).isManager()) {
            model.addAttribute("name", employeeDao.getByLogin(user.getLogin()).getName());
            model.addAttribute("id", employeeDao.getByLogin(user.getLogin()).getId());
            model.addAttribute("userRole", "manager");
            return "forward:managerPanel";

        } else if (employeeDao.isExist(user) && !employeeDao.getByLogin(user.getLogin()).isManager()) {
            model.addAttribute("name", employeeDao.getByLogin(user.getLogin()).getName());
            model.addAttribute("id", employeeDao.getByLogin(user.getLogin()).getId());
            model.addAttribute("userRole", "employee");
            return "forward:employeePanel";

        } else {
            model.addAttribute("message", "Wrong email or password");
            model.addAttribute("user", new User());
            session.setComplete();
            return "login";
        }

    }
}
