package com.netCracker.controllers;

import com.netCracker.comparators.ProjectIdComparator;
import com.netCracker.dao.ProjectDao;
import com.netCracker.model.Project;
import com.netCracker.validators.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("id")
public class ProjectController {

    @Autowired
    private ProjectDao projectDao;

    @RequestMapping(value = "addProject", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String add(@RequestParam String nameproj, @RequestParam String datebeginproj, @RequestParam String dateendproj, @RequestParam int cusid, @RequestParam int managid) {
        Project project = new Project(nameproj, cusid, datebeginproj, dateendproj, managid);
        EntityValidator<Project> validator = new EntityValidator<>();
        String result = validator.validate(project);
        if (result.isEmpty()){
            projectDao.create(project);
        }
        return result;
    }

    @RequestMapping(value = "editProject", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String edit(@RequestParam String nameproj, @RequestParam String datebeginproj, @RequestParam String dateendproj, @RequestParam int cusid, @RequestParam int managid, @RequestParam int idproj) {
        Project project = new Project(nameproj, cusid, datebeginproj, dateendproj, managid);
        project.setId(idproj);
        EntityValidator<Project> validator = new EntityValidator<>();
        String result = validator.validate(project);
        if (result.isEmpty()) {
            projectDao.update(project);
        }
        return result;
    }

    @RequestMapping(value = "projects", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Project> showList() {
        List<Project> list = projectDao.getAll();
        if (list != null) {
            list.sort(new ProjectIdComparator());
        }
        return list;
    }

    @RequestMapping(value = "project", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Project show(@RequestParam int id) {
        return projectDao.getById(id);
    }

    @RequestMapping(value = "deleteProj", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String delete(@RequestParam int id) {
        projectDao.delete(id);
        return "deleted";
    }

    @RequestMapping(value = "projectsByManagerId",method = RequestMethod.GET, produces ={"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Project> showListByMangerID(@RequestParam int idManager){
        return projectDao.getByManagerId(idManager);
    }
    @RequestMapping(value = "projectsByCustomerId",method = RequestMethod.GET, produces ={"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Project> projectsByCustomerId(@ModelAttribute("id") int idCustomer){
        return projectDao.getByCustomerId(idCustomer);
    }
}
