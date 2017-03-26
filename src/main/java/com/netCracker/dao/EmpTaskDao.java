package com.netCracker.dao;

import com.netCracker.model.EmpTask;

import java.util.List;

public interface EmpTaskDao {
    boolean create(EmpTask entity);

    List<EmpTask> getIdEmps(int idTask);

    List<EmpTask> getIdTasks(int idEmps);

    List<EmpTask> getAll();

    boolean delete(EmpTask empTask);
}
