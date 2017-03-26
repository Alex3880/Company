package com.netCracker.dao.jsonFile;

import com.netCracker.dao.ConditionDao;
import com.netCracker.model.Condition;
import com.netCracker.utils.JsonFileUtils;

import java.util.List;

public class ConditionDaoImpl implements ConditionDao {
    private final static String FILE_NAME = "json/Condition.txt";

    @Override
    public boolean create(Condition entity) {
        return JsonFileUtils.writeEntity(entity, FILE_NAME);
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
        return (List) JsonFileUtils.getAllEntitys(FILE_NAME, Condition.class);
    }

    @Override
    public boolean update(Condition entity) {
        List<Condition> list = getAll();
        for (Condition condition : list) {
            if (entity.getId() == condition.getId()) {
                condition.setName(entity.getName());
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

    @Override
    public boolean delete(Condition entity) {
        List<Condition> list = getAll();
        for (int i=0;i<list.size();i++) {
            if (entity.getId() == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }
}
