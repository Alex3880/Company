package com.netCracker.dao.serialization;

import com.netCracker.dao.EstimateDao;
import com.netCracker.model.Estimate;
import com.netCracker.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class EstimateDaoImpl implements EstimateDao {
    private final static String FILE_NAME = "ser/Estimate.bin";

    @Override
    public boolean create(Estimate entity) {
        List<Estimate> list = getAll();
        if (list==null){
            list = new ArrayList<>();
        }
        list.add(entity);
        return SerializationUtils.writeEntity(list, FILE_NAME);
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
        return SerializationUtils.readEntity(FILE_NAME);
    }

    @Override
    public boolean update(Estimate entity) {
        List<Estimate> list = getAll();
        for (Estimate estimate : list) {
            if (entity.getId() == estimate.getId()) {
                estimate.setHours(entity.getHours());
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public boolean delete(Estimate entity) {
        List<Estimate> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (entity.getId() == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

}
