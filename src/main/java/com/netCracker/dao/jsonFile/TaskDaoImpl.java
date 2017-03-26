package com.netCracker.dao.jsonFile;

import com.netCracker.dao.TaskDao;
import com.netCracker.model.Task;
import com.netCracker.utils.JsonFileUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TaskDaoImpl implements TaskDao {
    private final static String FILE_NAME = "json/Task.txt";

    @Override
    public boolean create(Task entity) {
        return JsonFileUtils.writeEntity(entity, FILE_NAME);
    }

    @Override
    public Task getById(int id) {
        List<Task> list = getAll();
        Task obj = null;
        for (Task task : list) {
            if (id == task.getId()) {
                obj = task;
            }
        }
        return obj;
    }

    @Override
    public List<Task> getAll() {
        return (List) JsonFileUtils.getAllEntitys(FILE_NAME, Task.class);
    }

    @Override
    public boolean update(Task entity) {
        List<Task> list = getAll();
        for (Task task : list) {
            if (entity.getId() == task.getId()) {
                task.setName(entity.getName());
                task.setIdSprint(entity.getIdSprint());
                task.setIdEstimate(entity.getIdEstimate());
                task.setIdCondition(entity.getIdCondition());
                task.setDependencyTask(entity.getDependencyTask());
                task.setIsSubtask(entity.getIsSubtask());
                task.setIdSkill(entity.getIdSkill());
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

    @Override
    public boolean delete(Task entity) {
       return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        List<Task> list = getAll();
        for (int i=0;i<list.size();i++) {
            if (id == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

}
