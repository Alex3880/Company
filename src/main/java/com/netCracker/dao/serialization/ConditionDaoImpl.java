package com.netCracker.dao.serialization;

import com.netCracker.dao.ConditionDao;
import com.netCracker.model.Condition;
import com.netCracker.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class ConditionDaoImpl implements ConditionDao {
    private final static String FILE_NAME = "ser/Condition.bin";

    @Override
    public boolean create(Condition entity) {
        List<Condition> list = getAll();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(entity);
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public Condition getById(int id) {
        List<Condition> list = getAll();
        Condition obj = null;
        for (Condition condition : list) {
            if (id == condition.getId()) {
                obj = condition;
            }
        }
        return obj;
    }

    @Override
    public List<Condition> getAll() {
        return SerializationUtils.readEntity(FILE_NAME);
    }

    @Override
    public boolean update(Condition entity) {
        List<Condition> list = getAll();
        for (Condition condition : list) {
            if (entity.getId() == condition.getId()) {
                condition.setName(entity.getName());
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public boolean delete(Condition entity) {
        List<Condition> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (entity.getId() == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }
}
