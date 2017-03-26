package com.netCracker.controllers;

import com.netCracker.comparators.SprintIdComparator;
import com.netCracker.dao.SprintDao;
import com.netCracker.model.Sprint;
import com.netCracker.validators.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SprintController {
    @Autowired
    private SprintDao sprintDao;

    @RequestMapping(value = "addSprint", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String add(@RequestParam String nameSprint, @RequestParam int projectId, @RequestParam int prevSprint) {
        Sprint sprint = new Sprint(nameSprint,projectId,prevSprint);
        EntityValidator<Sprint> validator = new EntityValidator<>();
        String result = validator.validate(sprint);
        if (result.isEmpty()){
            sprintDao.create(sprint);
        }
        return result;
    }

    @RequestMapping(value = "editSprint", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String edit(@RequestParam String nameSprint, @RequestParam int projectId, @RequestParam int prevSprint, @RequestParam int idSprint) {
        Sprint sprint = new Sprint(nameSprint,projectId,prevSprint);
        sprint.setId(idSprint);
        EntityValidator<Sprint> validator = new EntityValidator<>();
        String result = validator.validate(sprint);
        if (result.isEmpty()) {
            sprintDao.update(sprint);
        }
        return result;
    }

    @RequestMapping(value = "sprints", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Sprint> showList() {
        List<Sprint> list = sprintDao.getAll();
        if (list != null) {
            list.sort(new SprintIdComparator());
        }
        return list;
    }

    @RequestMapping(value = "sprintsByProject", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Sprint> showListByProject(@RequestParam int projId) {
        List<Sprint> list = sprintDao.getByProjectId(projId);
        if (list != null) {
            list.sort(new SprintIdComparator());
        }
        return list;
    }

    @RequestMapping(value = "sprint", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Sprint show(@RequestParam int id) {
        return sprintDao.getById(id);
    }

    @RequestMapping(value = "deleteSprint", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String delete(@RequestParam int id) {
        sprintDao.delete(id);
        return "deleted";
    }
}
