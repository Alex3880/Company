package com.netCracker.dao;

import com.netCracker.model.Project;

import java.util.List;
import java.util.stream.Collectors;


public interface ProjectDao extends CRUDable<Project> {
    boolean delete(int id);

    default List<Project> getByManagerId(int managerId) {
        return getAll().stream().filter(project -> project.getIdManager() == managerId).collect(Collectors.toList());
    }

    default List<Project> getByCustomerId(int customerId) {
        return getAll().stream().filter(project -> project.getIdCustomer() == customerId).collect(Collectors.toList());
    }

}
