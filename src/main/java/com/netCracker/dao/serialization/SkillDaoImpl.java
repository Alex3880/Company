package com.netCracker.dao.serialization;

import com.netCracker.dao.SkillDao;
import com.netCracker.model.Skill;
import com.netCracker.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl implements SkillDao {
    private final static String FILE_NAME = "ser/Skill.bin";

    @Override
    public boolean create(Skill entity) {
        List<Skill> list = getAll();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(entity);
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public Skill getById(int id) {
        List<Skill> list = getAll();
        Skill obj = null;
        for (Skill skill : list) {
            if (id == skill.getId()) {
                obj = skill;
            }
        }
        return obj;
    }

    @Override
    public List<Skill> getAll() {
        return SerializationUtils.readEntity(FILE_NAME);
    }

    @Override
    public boolean update(Skill entity) {
        List<Skill> list = getAll();
        for (Skill condition : list) {
            if (entity.getId() == condition.getId()) {
                condition.setSkillName(entity.getSkillName());
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public boolean delete(Skill entity) {
        List<Skill> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (entity.getId() == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }
}
