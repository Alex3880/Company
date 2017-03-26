package com.netCracker.controllers;

import com.netCracker.dao.SkillDao;
import com.netCracker.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SkillController {
    @Autowired
    private SkillDao skillDao;

    @RequestMapping(value = "skills", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Skill> showList() {
        return skillDao.getAll();
    }
}
