package com.netCracker.dao.db;

import com.netCracker.dao.EmployeeDao;
import com.netCracker.model.User;
import com.netCracker.security.MD5Hash;
import com.netCracker.utils.JdbcUtils;
import com.netCracker.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public boolean create(Employee entity) {
        Connection connection = null;
        PreparedStatement insertEntity = null;
        Statement findIdEntity = null;
        Statement findIdProject = null;
        PreparedStatement insertValues = null;

        boolean created = false;


        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            insertEntity = connection.prepareStatement("INSERT INTO \"ENTITY\"(id_type) VALUES (7)");
            insertEntity.executeUpdate();

            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=7");
            int id = 0;
            while (id_entity.next()) {
                int temp = id_entity.getInt("id");
                if (temp > id) {
                    id = temp;
                }
            }

            findIdProject = connection.createStatement();
            ResultSet resultSet = findIdProject.executeQuery("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=25");
            int newId = 0;
            while (resultSet.next()) {
                int temp = Integer.valueOf(resultSet.getString("val"));
                if (temp > newId) {
                    newId = temp;
                }
            }

            insertValues = connection.prepareStatement("INSERT INTO \"ENTITY_VAL\"(id_attr, val, id_entity) VALUES (?,?,?)");
            insertValues.setInt(1, 25);
            insertValues.setString(2, String.valueOf(++newId));
            entity.setId(newId);
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 26);
            insertValues.setString(2, entity.getName());
            insertValues.executeUpdate();

            insertValues.setInt(1, 27);
            insertValues.setString(2, entity.getLastName());
            insertValues.executeUpdate();

            insertValues.setInt(1, 28);
            insertValues.setString(2, String.valueOf(entity.getIdSkill()));
            insertValues.executeUpdate();

            insertValues.setInt(1, 36);
            insertValues.setString(2, String.valueOf(entity.getLogin()));
            insertValues.executeUpdate();

            MD5Hash md5Hash = new MD5Hash();
            insertValues.setInt(1, 37);
            insertValues.setString(2, String.valueOf(md5Hash.MD5(entity.getPass())));
            insertValues.executeUpdate();

            created = true;
            connection.commit();
        } catch (Exception e) {
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
    public Employee getById(int id) {
        Connection connection = null;
        Employee employee = null;
        PreparedStatement findObj = null;

        try {
            connection = dataSource.getConnection();
            findObj = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" where id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" where id_attr=25 AND val=?) AND id_attr=?");
            findObj.setString(1, String.valueOf(id));
            findObj.setInt(2, 26);
            ResultSet valuesRs = findObj.executeQuery();
            if (valuesRs.next()) {
                employee = new Employee();
                employee.setId(id);
                employee.setName(valuesRs.getString("val"));

                findObj.setInt(2, 27);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                employee.setLastName(valuesRs.getString("val"));

                findObj.setInt(2, 28);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                employee.setIdSkill(Integer.valueOf(valuesRs.getString("val")));

                findObj.setInt(2, 36);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                employee.setLogin(valuesRs.getString("val"));

                findObj.setInt(2, 37);
                valuesRs = findObj.executeQuery();
                valuesRs.next();
                employee.setPass(valuesRs.getString("val"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, findObj);

        }
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        Connection connection = null;
        List<Employee> list = null;
        Statement findIdEntity = null;
        PreparedStatement findIdCustomer = null;
        List<Integer> ids = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=7");

            while (id_entity.next()) {
                findIdCustomer = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=25 AND id_entity=?");
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
    public boolean update(Employee entity) {
        Connection connection = null;
        PreparedStatement changeVal = null;
        boolean updated = false;

        try {
            connection = dataSource.getConnection();
            changeVal = connection.prepareStatement("UPDATE \"ENTITY_VAL\" SET val=? WHERE id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" WHERE val=? AND id_attr=25) AND id_attr=?");
            changeVal.setString(1, entity.getName());
            changeVal.setString(2, String.valueOf(entity.getId()));
            changeVal.setInt(3, 26);
            changeVal.executeUpdate();

            changeVal.setString(1, entity.getLastName());
            changeVal.setInt(3, 27);
            changeVal.executeUpdate();

            changeVal.setString(1, String.valueOf(entity.getIdSkill()));
            changeVal.setInt(3, 28);
            changeVal.executeUpdate();

            changeVal.setString(1, entity.getLogin());
            changeVal.setInt(3, 36);
            changeVal.executeUpdate();

            MD5Hash md5Hash = new MD5Hash();
            changeVal.setString(1, md5Hash.isValidMD5(entity.getPass()) ? entity.getPass() : md5Hash.MD5(entity.getPass()));
            changeVal.setInt(3, 37);
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
    public boolean delete(Employee entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        try {
            return JdbcUtils.delByIdEntity(String.valueOf(id), 25, dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
