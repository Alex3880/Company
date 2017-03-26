package com.netCracker.dao.serialization;

import com.netCracker.dao.EmpTaskDao;
import com.netCracker.model.EmpTask;
import com.netCracker.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class EmpTaskDaoImpl implements EmpTaskDao {
    private final static String FILE_NAME = "ser/EmpTask.bin";

    @Override
    public boolean create(EmpTask entity) {
        List<EmpTask> list = getAll();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(entity);
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public List<EmpTask> getIdEmps(int idTask) {
        List<EmpTask> list = getAll();
        List<EmpTask> emps = null;
        if (list != null) {
            emps = new ArrayList<>();
            for (EmpTask empTask : list) {
                if (idTask == empTask.getIdTask()) {
                    emps.add(empTask);
                }
            }
        }
        return emps;
    }

    @Override
    public List<EmpTask> getIdTasks(int idEmps) {
        List<EmpTask> list = getAll();
        List<EmpTask> tasks = null;
        if (list != null) {
            tasks = new ArrayList<>();
            for (EmpTask empTask : list) {
                if (idEmps == empTask.getIdEmp()) {
                    tasks.add(empTask);
                }
            }
        }
        return tasks;
    }

    @Override
    public List<EmpTask> getAll() {
        return SerializationUtils.readEntity(FILE_NAME);
    }

    @Override
    public boolean delete(EmpTask empTask) {
        List<EmpTask> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (empTask.getIdEmp() == list.get(i).getIdEmp() && empTask.getIdTask() == list.get(i).getIdTask()) {
                list.remove(i);
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }
}
