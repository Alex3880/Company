package com.netCracker.controllers;

import com.netCracker.dao.*;
import com.netCracker.model.*;
import com.netCracker.validators.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class TaskController {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private EstimateDao estimateDao;
    @Autowired
    private ConditionDao conditionDao;
    @Autowired
    private SkillDao skillDao;
    @Autowired
    private EmpTaskDao empTaskDao;
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "tasksBySprintId", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List tasks(int idSprint) {
        List<Task> tasks = taskDao.getBySprintId(idSprint);
        if (tasks.size() == 0) {
            return null;
        }
        List<Object> options = null;
        List<List> listWithOptions = new ArrayList();
        for (Task task : tasks) {
            options = new ArrayList();
            options.add(task.getId());
            options.add(task.getName());
//            options.add(task.getIdSprint());
            options.add(estimateDao.getById(task.getIdEstimate()).getHours());
            options.add(conditionDao.getById(task.getIdCondition()).getName());
            options.add(task.getDependencyTask());
            options.add(task.getIsSubtask());
            options.add(skillDao.getById(task.getIdSkill()).getSkillName());
            listWithOptions.add(options);
        }
        return listWithOptions;
    }

    @RequestMapping(value = "employeeByTaskId", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Employee> employeeByTaskId(int idTask) {
        List<EmpTask> employeesIds = empTaskDao.getIdEmps(idTask);
        if (employeesIds.size() == 0) {
            return null;
        }
        List<Employee> employees = new ArrayList<>();

        for (EmpTask empTask : employeesIds) {
            employees.add(employeeDao.getById(empTask.getIdEmp()));
        }
        return employees;
    }

    @RequestMapping(value = "addTask", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String add(@RequestParam String nameTask, @RequestParam int sprintId, @RequestParam int estimate, @RequestParam int dependencyTask, @RequestParam int subtask, @RequestParam int selectSkill) {
        if (estimateDao.getByEstimateHours(estimate) == null) {
            estimateDao.create(new Estimate(estimate));
        }

        Task task = new Task(nameTask, sprintId, estimateDao.getByEstimateHours(estimate).getId(), Condition.CONDITION_WAITING, dependencyTask, subtask, selectSkill);
        EntityValidator<Task> validator = new EntityValidator<>();
        String result = validator.validate(task);
        if (result.isEmpty()) {
            taskDao.create(task);
        }
        return result;
    }

    @RequestMapping(value = "editTask", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String edit(@RequestParam int idTask, @RequestParam String nameTask, @RequestParam int sprintId, @RequestParam int estimate, @RequestParam int dependencyTask, @RequestParam int subtask, @RequestParam int selectSkill) {
        if (estimateDao.getByEstimateHours(estimate) == null) {
            estimateDao.create(new Estimate(estimate));
        }

        Task task = new Task(nameTask, sprintId, estimateDao.getByEstimateHours(estimate).getId(), Condition.CONDITION_WAITING, dependencyTask, subtask, selectSkill);
        task.setId(idTask);
        EntityValidator<Task> validator = new EntityValidator<>();
        String result = validator.validate(task);
        if (result.isEmpty()) {
            taskDao.update(task);
        }
        return result;
    }

    @RequestMapping(value = "deleteTask", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String delete(@RequestParam int id) {
        List<EmpTask> tasksToDelete = empTaskDao.getIdEmps(id);
        for (EmpTask empTask : tasksToDelete) {
            empTaskDao.delete(empTask);
        }
        taskDao.delete(id);
        return "deleted";
    }

    @RequestMapping(value = "task", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Task show(@RequestParam int id) {
        Task task = taskDao.getById(id);
        task.setIdEstimate(estimateDao.getById(task.getIdEstimate()).getHours());
        return taskDao.getById(id);
    }

    @RequestMapping(value = "setEmpTask", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String setEmpTask(@RequestParam int idEmp, @RequestParam int idTask) {
        List<EmpTask> empTaskList = empTaskDao.getAll().stream().filter(entity -> entity.getIdEmp() == idEmp && entity.getIdTask() == idTask).collect(Collectors.toList());
        if (empTaskList.size() == 0) {
            empTaskDao.create(new EmpTask(idEmp, idTask));
            return "set";
        } else {
            return "already exist";
        }
    }
}
