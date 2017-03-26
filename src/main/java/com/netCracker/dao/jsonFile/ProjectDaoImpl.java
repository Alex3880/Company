package com.netCracker.dao.jsonFile;

import com.netCracker.dao.ProjectDao;
import com.netCracker.model.Project;
import com.netCracker.utils.JsonFileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDaoImpl implements ProjectDao {
    private final static String FILE_NAME = "json/Project.txt";

    @Override
    public boolean create(Project entity) {
        return JsonFileUtils.writeEntity(entity, FILE_NAME);
    }

    @Override
    public Project getById(int id) {
        List<Project> list = getAll();
        Project pr = null;
        for (Project project : list) {
            if (id == project.getId()) {
                pr = project;
            }
        }
        return pr;
    }

    @Override
    public List<Project> getAll() {
        return (List) JsonFileUtils.getAllEntitys(FILE_NAME, Project.class);
    }

    @Override
    public boolean update(Project entity) {
        List<Project> list = getAll();
        for (Project project : list) {
            if (entity.getId() == project.getId()) {
                project.setName(entity.getName());
                project.setIdCustomer(entity.getIdCustomer());
                project.setDateBegin(entity.getDateBegin());
                project.setDateEnd(entity.getDateEnd());
                project.setIdManager(entity.getIdManager());
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

    @Override
    public boolean delete(Project entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        List<Project> list = getAll();
        for (int i=0;i<list.size();i++) {
            if (id == list.get(i).getId()) {
                list.remove(i);
            }
        }
        return JsonFileUtils.rewriteFile(list, FILE_NAME);
    }

}
