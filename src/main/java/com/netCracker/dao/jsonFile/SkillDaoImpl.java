package com.netCracker.dao.jsonFile;

import com.netCracker.dao.SkillDao;
import com.netCracker.model.Skill;
import com.netCracker.utils.JsonFileUtils;

import java.util.List;


public class SkillDaoImpl implements SkillDao {
    private final static String FILE_NAME = "json/Skill.txt";

    @Override
    public boolean create(Skill entity) {
        return JsonFileUtils.writeEntity(entity, FILE_NAME);
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
        return (List) JsonFileUtils.getAllEntitys(FILE_NAME, Skill.class);
    }

    @Override
    public boolean update(Skill entity) {
        List<Skill> list = getAll();
        for (Skill sprint : list) {
            if (entity.getId() == sprint.getId()) {
                sprint.setSkillName(entity.getSkillName());
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

    @Override
    public boolean delete(Skill entity) {
        List<Skill> list = getAll();
        for (int i=0;i<list.size();i++) {
            if (entity.getId() == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }
}
