package com.netCracker.dao.db;

import com.netCracker.dao.ProjectDao;
import com.netCracker.utils.JdbcUtils;
import com.netCracker.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectDaoImpl implements ProjectDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public boolean create(Project project) {
        Connection connection = null;
        PreparedStatement insertEntity = null;
        Statement findIdEntity = null;
        Statement findIdProject = null;
        PreparedStatement insertValues = null;

        boolean created = false;


        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            insertEntity = connection.prepareStatement("INSERT INTO \"ENTITY\"(id_type) VALUES (2)");
            insertEntity.executeUpdate();

            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=2");
            int id = 0;
            while (id_entity.next()) {
                int temp = id_entity.getInt("id");
                if (temp > id) {
                    id = temp;
                }
            }

            findIdProject = connection.createStatement();
            ResultSet resultSet = findIdProject.executeQuery("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=3");
            int newId = 0;
            while (resultSet.next()) {
                int temp = Integer.valueOf(resultSet.getString("val"));
                if (temp > newId) {
                    newId = temp;
                }
            }

            insertValues = connection.prepareStatement("INSERT INTO \"ENTITY_VAL\"(id_attr, val, id_entity) VALUES (?,?,?)");
            insertValues.setInt(1, 3);
            insertValues.setString(2, String.valueOf(++newId));
            project.setId(newId);
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 31);
            insertValues.setString(2, project.getName());
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 4);
            insertValues.setString(2, String.valueOf(project.getIdCustomer()));
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 5);
            insertValues.setString(2, project.getDateBegin());
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 6);
            insertValues.setString(2, project.getDateEnd());
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 24);
            insertValues.setString(2, String.valueOf(project.getIdManager()));
            insertValues.setInt(3, id);
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
    public Project getById(int id) {
        Connection connection = null;
        Project project = null;
        PreparedStatement findObj = null;

        try {
            connection = dataSource.getConnection();
            findObj = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" where id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" where id_attr=3 AND val=?) AND id_attr=?");
            findObj.setString(1, String.valueOf(id));
            findObj.setInt(2, 31);
            ResultSet valuesRs = findObj.executeQuery();
            if (valuesRs.next()) {
                project = new Project();
                project.setId(id);
                project.setName(valuesRs.getString("val"));

                findObj.setInt(2, 4);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                project.setIdCustomer(Integer.valueOf(valuesRs.getString("val")));

                findObj.setInt(2, 5);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                project.setDateBegin(valuesRs.getString("val"));

                findObj.setInt(2, 6);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                project.setDateEnd(valuesRs.getString("val"));

                findObj.setInt(2, 24);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                project.setIdManager(Integer.valueOf(valuesRs.getString("val")));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, findObj);

        }
        return project;
    }


    @Override
    public List<Project> getAll() {
        Connection connection = null;
        List<Project> list = null;
        Statement findIdEntity = null;
        PreparedStatement findIdCustomer = null;
        List<Integer> ids = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=2");

            while (id_entity.next()) {
                findIdCustomer = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=3 AND id_entity=?");
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
    public boolean update(Project project) {
        Connection connection = null;
        PreparedStatement changeVal = null;
        boolean updated = false;

        try {
            connection = dataSource.getConnection();
            changeVal = connection.prepareStatement("UPDATE \"ENTITY_VAL\" SET val=? WHERE id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" WHERE val=? AND id_attr=3) AND id_attr=?");
            changeVal.setString(1, project.getName());
            changeVal.setString(2, String.valueOf(project.getId()));
            changeVal.setInt(3,31);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(project.getIdCustomer()));
            changeVal.setInt(3,4);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(project.getIdManager()));
            changeVal.setInt(3,24);
            changeVal.executeUpdate();

            changeVal.setString(1, project.getDateBegin());
            changeVal.setInt(3,5);
            changeVal.executeUpdate();

            changeVal.setString(1, project.getDateEnd());
            changeVal.setInt(3,6);
            changeVal.executeUpdate();
            updated = true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.closeQuietly(connection, changeVal);
        }

        return updated;
    }

    @Override
    public boolean delete(Project project) {
        return delete(project.getId());
    }

    @Override
    public boolean delete(int id) {
        try {
            return JdbcUtils.delByIdEntity(String.valueOf(id), 3, dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
