package com.netCracker.dao.jsonFile;

import com.netCracker.dao.EstimateDao;
import com.netCracker.model.Estimate;
import com.netCracker.utils.JsonFileUtils;

import java.util.List;

public class EstimateDaoImpl implements EstimateDao {
    private final static String FILE_NAME = "json/Estimate.txt";

    @Override
    public boolean create(Estimate entity) {
        return JsonFileUtils.writeEntity(entity, FILE_NAME);
    }

    @Override
    public Estimate getById(int id) {
        List<Estimate> list = getAll();
        Estimate obj = null;
        for (Estimate estimate : list) {
            if (id == estimate.getId()) {
                obj = estimate;
            }
        }
        return obj;
    }

    @Override
    public List<Estimate> getAll() {
        return (List) JsonFileUtils.getAllEntitys(FILE_NAME, Estimate.class);
    }

    @Override
    public boolean update(Estimate entity) {
        List<Estimate> list = getAll();
        for (Estimate estimate : list) {
            if (entity.getId() == estimate.getId()) {
                estimate.setHours(entity.getHours());
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

    @Override
    public boolean delete(Estimate entity) {
        List<Estimate> list = getAll();
        for (int i=0;i<list.size();i++) {
            if (entity.getId() == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

}
