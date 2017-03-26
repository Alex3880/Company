package com.netCracker;

import com.netCracker.dao.*;
import com.netCracker.dao.serialization.ConditionDaoImpl;
import com.netCracker.dao.serialization.CustomerDaoImpl;
import com.netCracker.dao.serialization.EmpTaskDaoImpl;
import com.netCracker.dao.serialization.EmployeeDaoImpl;
import com.netCracker.dao.serialization.EstimateDaoImpl;
import com.netCracker.dao.serialization.ProjectDaoImpl;
import com.netCracker.dao.serialization.SkillDaoImpl;
import com.netCracker.dao.serialization.SprintDaoImpl;
import com.netCracker.dao.serialization.TaskDaoImpl;
import com.netCracker.model.*;
import com.netCracker.utils.JdbcUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapEntitys {

    private final static int JSONFILE = 1;
    private final static int SERIALIZATION = 2;
    private final static int DATABASE = 3;
    private final static int XML = 4;

    private HashMap<Class, List> map;
    private CustomerDao customerDao;
    private ProjectDao projectDao;
    private SprintDao sprintDao;
    private TaskDao taskDao;
    private EstimateDao estimateDao;
    private ConditionDao conditionDao;
    private EmployeeDao employeeDao;
    private SkillDao skillDao;
    private EmpTaskDao empTaskDao;

    public MapEntitys(int id) {
        if (id == 1) {
            customerDao = new com.netCracker.dao.jsonFile.CustomerDaoImpl();
            projectDao = new com.netCracker.dao.jsonFile.ProjectDaoImpl();
            sprintDao = new com.netCracker.dao.jsonFile.SprintDaoImpl();
            taskDao = new com.netCracker.dao.jsonFile.TaskDaoImpl();
            estimateDao = new com.netCracker.dao.jsonFile.EstimateDaoImpl();
            conditionDao = new com.netCracker.dao.jsonFile.ConditionDaoImpl();
            employeeDao = new com.netCracker.dao.jsonFile.EmployeeDaoImpl();
            skillDao = new com.netCracker.dao.jsonFile.SkillDaoImpl();
            empTaskDao = new com.netCracker.dao.jsonFile.EmpTaskDaoImpl();
        } else if (id == 2) {
            customerDao = new CustomerDaoImpl();
            projectDao = new ProjectDaoImpl();
            sprintDao = new SprintDaoImpl();
            taskDao = new TaskDaoImpl();
            estimateDao = new EstimateDaoImpl();
            conditionDao = new ConditionDaoImpl();
            employeeDao = new EmployeeDaoImpl();
            skillDao = new SkillDaoImpl();
            empTaskDao = new EmpTaskDaoImpl();
        } else if (id == 3) {
            customerDao = new com.netCracker.dao.db.CustomerDaoImpl();
            projectDao = new com.netCracker.dao.db.ProjectDaoImpl();
            sprintDao = new com.netCracker.dao.db.SprintDaoImpl();
            taskDao = new com.netCracker.dao.db.TaskDaoImpl();
            estimateDao = new com.netCracker.dao.db.EstimateDaoImpl();
            conditionDao = new com.netCracker.dao.db.ConditionDaoImpl();
            employeeDao = new com.netCracker.dao.db.EmployeeDaoImpl();
            skillDao = new com.netCracker.dao.db.SkillDaoImpl();
            empTaskDao = new com.netCracker.dao.db.EmpTaskDaoImpl();

        } else if (id == 4) {
            customerDao = new com.netCracker.dao.xml.CustomerDaoImpl();
            projectDao = new com.netCracker.dao.xml.ProjectDaoImpl();
            sprintDao = new com.netCracker.dao.xml.SprintDaoImpl();
            taskDao = new com.netCracker.dao.xml.TaskDaoImpl();
            estimateDao = new com.netCracker.dao.xml.EstimateDaoImpl();
            conditionDao = new com.netCracker.dao.xml.ConditionDaoImpl();
            employeeDao = new com.netCracker.dao.xml.EmployeeDaoImpl();
            skillDao = new com.netCracker.dao.xml.SkillDaoImpl();
            empTaskDao = new com.netCracker.dao.xml.EmpTaskDaoImpl();
        }
        map = new HashMap<>();
    }

    public Map<Class, List> getMap() {
        map.put(Customer.class, customerDao.getAll());
        map.put(Project.class, projectDao.getAll());
        map.put(Sprint.class, sprintDao.getAll());
        map.put(Task.class, taskDao.getAll());
        map.put(Estimate.class, estimateDao.getAll());
        map.put(Condition.class, conditionDao.getAll());
        map.put(Employee.class, employeeDao.getAll());
        map.put(Skill.class, skillDao.getAll());
        map.put(EmpTask.class, empTaskDao.getAll());
        return map;
    }
}
