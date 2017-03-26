package com.netCracker.controllers;

import com.netCracker.dao.ConditionDao;
import com.netCracker.model.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ConditionController {

    @Autowired
    private ConditionDao conditionDao;

    @RequestMapping(value = "conditions", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Condition> showList() {
        return conditionDao.getAll();
    }
}
