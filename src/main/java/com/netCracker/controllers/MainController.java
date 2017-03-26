package com.netCracker.controllers;

import com.netCracker.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("userRole")
public class MainController {

    @ModelAttribute("userRole")
    public String get() {
        return "none";
    }

    @RequestMapping(value = "/")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "logout")
    public String logout(SessionStatus session) {
        session.setComplete();
        return "forward:/";
    }

    @RequestMapping(value = "adminPanel")
    public String lists(@ModelAttribute("userRole") String role) {
        if ("admin".equals(role)) {
            return "adminPanel";
        } else {
            return "forward:/";
        }
    }

    @RequestMapping(value = "customerPanel")
    public String customerPanel(@ModelAttribute("userRole") String role) {
        if ("customer".equals(role)) {
            return "customerPanel";
        } else {
            return "forward:/";
        }
    }

    @RequestMapping(value = "managerPanel")
    public String managerPanel(@ModelAttribute("userRole") String role) {
        if ("manager".equals(role)) {
            return "managerPanel";
        } else {
            return "forward:/";
        }
    }

    @RequestMapping(value = "employeePanel")
    public String employeePanel(@ModelAttribute("userRole") String role) {
        if ("employee".equals(role)) {
            return "employeePanel";
        } else {
            return "forward:/";
        }
    }
}
