package com.netCracker.dao;

import com.netCracker.model.Sprint;

import java.util.List;
import java.util.stream.Collectors;

public interface SprintDao extends CRUDable<Sprint> {
    boolean delete(int id);

    default List<Sprint> getByProjectId(int idProject) {
        return getAll().stream().filter(task -> task.getIdProject() == idProject).collect(Collectors.toList());
    }
}
