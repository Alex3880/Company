package com.netCracker.dao;

import com.netCracker.model.Task;

import java.util.List;
import java.util.stream.Collectors;


public interface TaskDao extends CRUDable<Task> {
    default List<Task> getBySprintId(int sprintId) {
        return getAll().stream().filter(task -> task.getIdSprint() == sprintId).collect(Collectors.toList());
    }

    boolean delete(int id);
}
