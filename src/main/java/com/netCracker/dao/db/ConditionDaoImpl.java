package com.netCracker.dao.db;


import com.netCracker.dao.ConditionDao;
import com.netCracker.utils.JdbcUtils;
import com.netCracker.model.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConditionDaoImpl implements ConditionDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public boolean create(Condition entity) {
        Connection connection = null;
        PreparedStatement insertEntity = null;
        Statement findIdEntity = null;
        Statement findIdProject = null;
        PreparedStatement insertValues = null;

        boolean created = false;


        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            insertEntity = connection.prepareStatement("INSERT INTO \"ENTITY\"(id_type) VALUES (6)");
            insertEntity.executeUpdate();

            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=6");
            int id = 0;
            while (id_entity.next()) {
                int temp = id_entity.getInt("id");
                if (temp > id) {
                    id = temp;
                }
            }

            findIdProject = connection.createStatement();
            ResultSet resultSet = findIdProject.executeQuery("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=20");
            int newId = 0;
            while (resultSet.next()) {
                int temp = Integer.valueOf(resultSet.getString("val"));
                if (temp > newId) {
                    newId = temp;
                }
            }

            insertValues = connection.prepareStatement("INSERT INTO \"ENTITY_VAL\"(id_attr, val, id_entity) VALUES (?,?,?)");
            insertValues.setInt(1, 20);
            insertValues.setString(2, String.valueOf(++newId));
            entity.setId(newId);
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 21);
            insertValues.setString(2, entity.getName());
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
    public Condition getById(int id) {
        Connection connection = null;
        Condition condition = null;
        PreparedStatement findObj = null;

        try {
            connection = dataSource.getConnection();
            findObj = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" where id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" where id_attr=20 AND val=?) AND id_attr=?");
            findObj.setString(1, String.valueOf(id));
            findObj.setInt(2, 21);
            ResultSet valuesRs = findObj.executeQuery();
            if (valuesRs.next()) {
                condition = new Condition();
                condition.setId(id);
                condition.setName(valuesRs.getString("val"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, findObj);

        }
        return condition;
    }

    @Override
    public List<Condition> getAll() {
        Connection connection = null;
        List<Condition> list = null;
        Statement findIdEntity = null;
        PreparedStatement findIdCustomer = null;
        List<Integer> ids = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=6");

            while (id_entity.next()) {
                findIdCustomer = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=20 AND id_entity=?");
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
    public boolean update(Condition entity) {
        Connection connection = null;
        PreparedStatement changeVal = null;
        boolean updated = false;

        try {
            connection = dataSource.getConnection();
            changeVal = connection.prepareStatement("UPDATE \"ENTITY_VAL\" SET val=? WHERE id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" WHERE val=? AND id_attr=20) AND id_attr=?");
            changeVal.setString(1, entity.getName());
            changeVal.setString(2, String.valueOf(entity.getId()));
            changeVal.setInt(3, 21);
            if (changeVal.executeUpdate() > 0) {
                updated = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, changeVal);
        }

        return updated;
    }

    @Override
    public boolean delete(Condition entity) {
        try {
            return JdbcUtils.delByIdEntity(String.valueOf(entity.getId()), 20, dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
