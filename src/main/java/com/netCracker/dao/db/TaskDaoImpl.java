package com.netCracker.dao.db;


import com.netCracker.dao.TaskDao;
import com.netCracker.utils.JdbcUtils;
import com.netCracker.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskDaoImpl implements TaskDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public boolean create(Task entity) {
        Connection connection = null;
        PreparedStatement insertEntity = null;
        Statement findIdEntity = null;
        Statement findIdProject = null;
        PreparedStatement insertValues = null;

        boolean created = false;


        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            insertEntity = connection.prepareStatement("INSERT INTO \"ENTITY\"(id_type) VALUES (4)");
            insertEntity.executeUpdate();

            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=4");
            int id = 0;
            while (id_entity.next()) {
                int temp = id_entity.getInt("id");
                if (temp > id) {
                    id = temp;
                }
            }

            findIdProject = connection.createStatement();
            ResultSet resultSet = findIdProject.executeQuery("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=10");
            int newId = 0;
            while (resultSet.next()) {
                int temp = Integer.valueOf(resultSet.getString("val"));
                if (temp > newId) {
                    newId = temp;
                }
            }

            insertValues = connection.prepareStatement("INSERT INTO \"ENTITY_VAL\"(id_attr, val, id_entity) VALUES (?,?,?)");
            insertValues.setInt(1, 10);
            insertValues.setString(2, String.valueOf(++newId));
            entity.setId(newId);
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 33);
            insertValues.setString(2, entity.getName());
            insertValues.executeUpdate();

            insertValues.setInt(1, 11);
            insertValues.setString(2, String.valueOf(entity.getIdSprint()));
            insertValues.executeUpdate();

            insertValues.setInt(1, 12);
            insertValues.setString(2, String.valueOf(entity.getIdEstimate()));
            insertValues.executeUpdate();

            insertValues.setInt(1, 13);
            insertValues.setString(2, String.valueOf(entity.getIdCondition()));
            insertValues.executeUpdate();

            insertValues.setInt(1, 14);
            insertValues.setString(2, String.valueOf(entity.getDependencyTask()));
            insertValues.executeUpdate();

            insertValues.setInt(1, 15);
            insertValues.setString(2, String.valueOf(entity.getIsSubtask()));
            insertValues.executeUpdate();

            insertValues.setInt(1, 16);
            insertValues.setString(2, String.valueOf(entity.getIdSkill()));
            insertValues.executeUpdate();
            created = true;
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            JdbcUtils.closeQuietly(connection, insertEntity, findIdEntity, findIdProject, insertValues);
        }
        return created;
    }

    @Override
    public Task getById(int id) {
        Connection connection = null;
        Task task = null;
        PreparedStatement findObj = null;

        try {
            connection = dataSource.getConnection();
            findObj = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" where id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" where id_attr=10 AND val=?) AND id_attr=?");
            findObj.setString(1, String.valueOf(id));
            findObj.setInt(2, 33);
            ResultSet valuesRs = findObj.executeQuery();
            if (valuesRs.next()) {
                task = new Task();
                task.setId(id);
                task.setName(valuesRs.getString("val"));

                findObj.setInt(2, 11);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                task.setIdSprint(Integer.valueOf(valuesRs.getString("val")));

                findObj.setInt(2, 12);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                task.setIdEstimate(Integer.valueOf(valuesRs.getString("val")));

                findObj.setInt(2, 13);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                task.setIdCondition(Integer.valueOf(valuesRs.getString("val")));

                findObj.setInt(2, 14);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                task.setDependencyTask(Integer.valueOf(valuesRs.getString("val")));

                findObj.setInt(2, 15);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                task.setIsSubtask(Integer.valueOf(valuesRs.getString("val")));

                findObj.setInt(2, 16);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                task.setIdSkill(Integer.valueOf(valuesRs.getString("val")));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, findObj);

        }
        return task;
    }

    @Override
    public List<Task> getAll() {
        Connection connection = null;
        List<Task> list = null;
        Statement findIdEntity = null;
        PreparedStatement findIdCustomer = null;
        List<Integer> ids = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=4");

            while (id_entity.next()) {
                findIdCustomer = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=10 AND id_entity=?");
                findIdCustomer.setInt(1, id_entity.getInt("id"));
                ResultSet idsRs = findIdCustomer.executeQuery();
                if (idsRs.next()) {
                    ids.add(Integer.valueOf(idsRs.getString("val")));
                }

            }
            if (!ids.isEmpty()) {
                list = new ArrayList<>();
                for (Integer id : ids) {
                    list.add(getById(id));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, findIdEntity, findIdCustomer);

        }

        return list;
    }

    @Override
    public boolean update(Task entity) {
        Connection connection = null;
        PreparedStatement changeVal = null;
        boolean updated = false;

        try {
            connection = dataSource.getConnection();
            changeVal = connection.prepareStatement("UPDATE \"ENTITY_VAL\" SET val=? WHERE id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" WHERE val=? AND id_attr=10) AND id_attr=?");
            changeVal.setString(1, entity.getName());
            changeVal.setString(2, String.valueOf(entity.getId()));
            changeVal.setInt(3, 33);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(entity.getIdSprint()));
            changeVal.setInt(3, 11);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(entity.getIdEstimate()));
            changeVal.setInt(3, 12);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(entity.getIdCondition()));
            changeVal.setInt(3, 13);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(entity.getDependencyTask()));
            changeVal.setInt(3, 14);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(entity.getIsSubtask()));
            changeVal.setInt(3, 15);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(entity.getIdSkill()));
            changeVal.setInt(3, 16);
            changeVal.executeUpdate();
            updated = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, changeVal);
        }

        return updated;
    }

    @Override
    public boolean delete(Task entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        try {
            return JdbcUtils.delByIdEntity(String.valueOf(id), 10, dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
