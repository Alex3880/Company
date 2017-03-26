package com.netCracker.dao.serialization;

import com.netCracker.dao.ProjectDao;
import com.netCracker.model.Project;
import com.netCracker.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDaoImpl implements ProjectDao {
    private final static String FILE_NAME = "ser/Project.bin";


    @Override
    public boolean create(Project entity) {
        List<Project> list = getAll();
        if (list==null){
            list = new ArrayList<>();
        }
        list.add(entity);
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public Project getById(int id) {
        List<Project> list = getAll();
        Project obj = null;
        for (Project project : list) {
            if (id == project.getId()) {
                obj = project;
            }
        }
        return obj;
    }

    @Override
    public List<Project> getAll() {
        return SerializationUtils.readEntity(FILE_NAME);
    }

    @Override
    public boolean update(Project entity) {
        List<Project> list = getAll();
        for (Project project : list) {
            if (entity.getId() == project.getId()) {
                project.setName(entity.getName());
                project.setIdManager(entity.getIdManager());
                project.setIdCustomer(entity.getIdCustomer());
                project.setDateBegin(entity.getDateBegin());
                project.setDateEnd(entity.getDateEnd());
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

    @Override
    public boolean delete(Project entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        List<Project> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return SerializationUtils.writeEntity(list, FILE_NAME);
    }

}
