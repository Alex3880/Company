package com.netCracker.dao.jsonFile;

import com.netCracker.dao.EmpTaskDao;
import com.netCracker.model.EmpTask;
import com.netCracker.utils.JsonFileUtils;

import java.util.ArrayList;
import java.util.List;

public class EmpTaskDaoImpl implements EmpTaskDao {
    private final static String FILE_NAME = "json/EmpTask.txt";

    @Override
    public boolean create(EmpTask entity) {
        return JsonFileUtils.writeEntity(entity, FILE_NAME);
    }

    @Override
    public List<EmpTask> getIdEmps(int idTask) {
        List<EmpTask> list = getAll();
        List<EmpTask> objs = new ArrayList<>();
        for (EmpTask empTask : list){
            if (idTask==empTask.getIdTask()){
                objs.add(empTask);
            }
        }
        return objs;
    }

    @Override
    public List<EmpTask> getIdTasks(int idEmps) {
        List<EmpTask> list = getAll();
        List<EmpTask> objs = new ArrayList<>();
        for (EmpTask empTask : list){
            if (idEmps==empTask.getIdEmp()){
                objs.add(empTask);
            }
        }
        return objs;
    }

    @Override
    public List<EmpTask> getAll() {
        return (List) JsonFileUtils.getAllEntitys(FILE_NAME, EmpTask.class);
    }

    @Override
    public boolean delete(EmpTask empTask) {
        List<EmpTask> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (empTask.getIdEmp() == list.get(i).getIdEmp() && empTask.getIdTask() == list.get(i).getIdTask()) {
                list.remove(i);
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }
}
