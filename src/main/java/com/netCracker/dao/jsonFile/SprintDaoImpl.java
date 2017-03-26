package com.netCracker.dao.jsonFile;

import com.netCracker.dao.SprintDao;
import com.netCracker.model.Sprint;
import com.netCracker.utils.JsonFileUtils;

import java.util.ArrayList;
import java.util.List;

public class SprintDaoImpl implements SprintDao {
    private final static String FILE_NAME = "json/Sprint.txt";

    @Override
    public boolean create(Sprint entity) {
        return JsonFileUtils.writeEntity(entity, FILE_NAME);
    }

    @Override
    public Sprint getById(int id) {
        List<Sprint> list = getAll();
        Sprint obj = null;
        for (Sprint sprint : list) {
            if (id == sprint.getId()) {
                obj = sprint;
            }
        }
        return obj;
    }

    @Override
    public List<Sprint> getAll() {
        return (List) JsonFileUtils.getAllEntitys(FILE_NAME, Sprint.class);
    }

    @Override
    public boolean update(Sprint entity) {
        List<Sprint> list = getAll();
        for (Sprint sprint : list) {
            if (entity.getId() == sprint.getId()) {
                sprint.setName(entity.getName());
                sprint.setIdProject(entity.getIdProject());
                sprint.setPrevSprint(entity.getPrevSprint());
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

    @Override
    public boolean delete(Sprint entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        List<Sprint> list = getAll();
        for (int i=0;i<list.size();i++) {
            if (id == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

}
