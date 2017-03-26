package com.netCracker.dao.serialization;

import com.netCracker.dao.SprintDao;
import com.netCracker.model.Sprint;
import com.netCracker.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class SprintDaoImpl implements SprintDao {
    private final static String FILE_NAME = "ser/Sprint.bin";

    @Override
    public boolean create(Sprint entity) {
        List<Sprint> list = getAll();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(entity);
        return SerializationUtils.writeEntity(list, FILE_NAME);
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
        return SerializationUtils.readEntity(FILE_NAME);
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
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public boolean delete(Sprint entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        List<Sprint> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

}
