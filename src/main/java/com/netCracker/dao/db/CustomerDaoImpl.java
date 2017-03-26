package com.netCracker.dao.db;

import com.netCracker.dao.CustomerDao;
import com.netCracker.security.MD5Hash;
import com.netCracker.utils.JdbcUtils;
import com.netCracker.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerDaoImpl implements CustomerDao {
    @Autowired
    private DataSource dataSource;

    public boolean create(Customer customer) {

        Connection connection = null;
        PreparedStatement insertEntity = null;
        Statement findIdEntity = null;
        Statement findIdCustomer = null;
        PreparedStatement insertValues = null;

        boolean created = false;


        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            insertEntity = connection.prepareStatement("INSERT INTO \"ENTITY\"(id_type) VALUES (1)");
            insertEntity.executeUpdate();

            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=1");
            int id = 0;
            while (id_entity.next()) {
                int temp = id_entity.getInt("id");
                if (temp > id) {
                    id = temp;
                }
            }

            findIdCustomer = connection.createStatement();
            ResultSet resultSet = findIdCustomer.executeQuery("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=1");
            int newId = 0;
            while (resultSet.next()) {
                int temp = Integer.valueOf(resultSet.getString("val"));
                if (temp > newId) {
                    newId = temp;
                }
            }

            insertValues = connection.prepareStatement("INSERT INTO \"ENTITY_VAL\"(id_attr, val, id_entity) VALUES (?,?,?)");
            insertValues.setInt(1, 1);
            insertValues.setString(2, String.valueOf(++newId));
            customer.setId(newId);
            insertValues.setInt(3, id);
            insertValues.executeUpdate();
            insertValues.setInt(1, 2);
            insertValues.setString(2, customer.getName());
            insertValues.setInt(3, id);
            insertValues.executeUpdate();
            insertValues.setInt(1, 34);
            insertValues.setString(2, customer.getLogin());
            insertValues.setInt(3, id);
            insertValues.executeUpdate();
            MD5Hash md5Hash = new MD5Hash();
            insertValues.setInt(1, 35);
            insertValues.setString(2, md5Hash.MD5(customer.getPass()));
            insertValues.setInt(3, id);
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
            JdbcUtils.closeQuietly(connection, insertEntity, findIdEntity, findIdCustomer, insertValues);
        }
        return created;
    }

    public Customer getById(int id) {

        Connection connection = null;
        Customer customer = null;
        PreparedStatement findObj = null;
        PreparedStatement getSecondAttr = null;

        try {
            connection = dataSource.getConnection();
            findObj = connection.prepareStatement("SELECT id_entity FROM \"ENTITY_VAL\" where id_attr=1 AND val=?");
            findObj.setString(1, String.valueOf(id));

            getSecondAttr = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" where id_entity=? AND id_attr=?");
            ResultSet id_entity = findObj.executeQuery();
            if (id_entity.next()) {
                getSecondAttr.setInt(1, id_entity.getInt("id_entity"));
                getSecondAttr.setInt(2, 2);
                ResultSet attr2 = getSecondAttr.executeQuery();
                attr2.next();

                customer = new Customer();
                customer.setId(id);
                customer.setName(attr2.getString("val"));

                getSecondAttr.setInt(2, 34);
                attr2 = getSecondAttr.executeQuery();
                attr2.next();
                customer.setLogin(attr2.getString("val"));
                getSecondAttr.setInt(2, 35);
                attr2 = getSecondAttr.executeQuery();
                attr2.next();
                customer.setPass(attr2.getString("val"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, findObj, getSecondAttr);

        }
        return customer;
    }

    public List<Customer> getAll() {

        Connection connection = null;
        List<Customer> list = null;
        Statement findIdEntity = null;
        PreparedStatement findIdCustomer = null;
        List<Integer> ids = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=1");

            while (id_entity.next()) {
                findIdCustomer = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=1 AND id_entity=?");
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

    public boolean update(Customer customer) {

        Connection connection = null;
        PreparedStatement changeName = null;
        boolean updated = false;

        try {
            connection = dataSource.getConnection();
            changeName = connection.prepareStatement("UPDATE \"ENTITY_VAL\" SET val=? WHERE id_entity=(SELECT id_entity FROM \"ENTITY_VAL\" WHERE val=? AND id_attr=1) AND id_attr=?");
            changeName.setString(1, customer.getName());
            changeName.setString(2, String.valueOf(customer.getId()));
            changeName.setInt(3, 2);
            changeName.executeUpdate();
            changeName.setString(1, customer.getLogin());
            changeName.setInt(3, 34);
            changeName.executeUpdate();
            MD5Hash md5Hash = new MD5Hash();
            changeName.setString(1, md5Hash.isValidMD5(customer.getPass()) ? customer.getPass() : md5Hash.MD5(customer.getPass()));
            changeName.setInt(3, 35);
            changeName.executeUpdate();
            updated = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(connection, changeName);
        }

        return updated;
    }

    public boolean delete(Customer customer) {
        return delete(customer.getId());
    }

    public boolean delete(int id) {
        try {
            return JdbcUtils.delByIdEntity(String.valueOf(id), 1, dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
