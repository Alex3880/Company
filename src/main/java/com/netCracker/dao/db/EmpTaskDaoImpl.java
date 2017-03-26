package com.netCracker.dao.db;

import com.netCracker.dao.EmpTaskDao;
import com.netCracker.model.EmpTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.netCracker.utils.JdbcUtils.closeQuietly;

@Component
public class EmpTaskDaoImpl implements EmpTaskDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public boolean create(EmpTask entity) {
        Connection connection = null;
        PreparedStatement insertEntity = null;
        Statement findIdEntity = null;
        PreparedStatement insertValues = null;

        boolean created = false;


        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            insertEntity = connection.prepareStatement("INSERT INTO \"ENTITY\"(id_type) VALUES (9)");
            insertEntity.executeUpdate();

            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=9");
            int id = 0;
            while (id_entity.next()) {
                int temp = id_entity.getInt("id");
                if (temp > id) {
                    id = temp;
                }
            }


            insertValues = connection.prepareStatement("INSERT INTO \"ENTITY_VAL\"(id_attr, val, id_entity) VALUES (?,?,?)");
            insertValues.setInt(1, 29);
            insertValues.setString(2, String.valueOf(entity.getIdEmp()));
            insertValues.setInt(3, id);
            insertValues.executeUpdate();

            insertValues.setInt(1, 30);
            insertValues.setString(2, String.valueOf(entity.getIdTask()));
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
            closeQuietly(connection, insertEntity, findIdEntity, insertValues);
        }
        return created;
    }

    @Override
    public List<EmpTask> getIdEmps(int idTask) {
        Connection connection = null;
        PreparedStatement findObj = null;
        List<EmpTask> list= null;

        try {
            connection = dataSource.getConnection();
            findObj = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" where id_entity IN (SELECT id_entity FROM \"ENTITY_VAL\" where id_attr=30 AND val=?) AND id_attr=29");
            findObj.setString(1, String.valueOf(idTask));
            ResultSet valuesRs = findObj.executeQuery();
            list = new ArrayList<>();
            while (valuesRs.next()) {
                EmpTask empTask = new EmpTask();
                empTask.setIdTask(idTask);
                empTask.setIdEmp(Integer.valueOf(valuesRs.getString("val")));
                list.add(empTask);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(connection, findObj);

        }
        return list;
    }

    @Override
    public List<EmpTask> getIdTasks(int idEmps) {
        Connection connection = null;
        PreparedStatement findObj = null;
        List<EmpTask> list= null;

        try {
            connection = dataSource.getConnection();
            findObj = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" where id_entity IN (SELECT id_entity FROM \"ENTITY_VAL\" where id_attr=29 AND val=?) AND id_attr=30");
            findObj.setString(1, String.valueOf(idEmps));
            ResultSet valuesRs = findObj.executeQuery();
            list = new ArrayList<>();
            while (valuesRs.next()) {
                EmpTask empTask = new EmpTask();
                empTask.setIdEmp(idEmps);
                empTask.setIdTask(Integer.valueOf(valuesRs.getString("val")));
                list.add(empTask);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(connection, findObj);

        }
        return list;
    }

    @Override
    public List<EmpTask> getAll() {
        Connection connection = null;
        List<EmpTask> list = null;
        Statement findIdEntity = null;
        PreparedStatement findIdCustomer = null;
        List<Integer> ids = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            findIdEntity = connection.createStatement();
            ResultSet id_entity = findIdEntity.executeQuery("SELECT id FROM \"ENTITY\" WHERE id_type=9");

            list = new ArrayList<>();
            while (id_entity.next()) {
                EmpTask empTask =new EmpTask();
                findIdCustomer = connection.prepareStatement("SELECT val FROM \"ENTITY_VAL\" WHERE id_attr=? AND id_entity=?");
                findIdCustomer.setInt(1,29);
                findIdCustomer.setInt(2, id_entity.getInt("id"));
                ResultSet idsRs = findIdCustomer.executeQuery();
                idsRs.next();
                empTask.setIdEmp(Integer.valueOf(idsRs.getString("val")));

                findIdCustomer.setInt(1,30);
                idsRs = findIdCustomer.executeQuery();
                idsRs.next();
                empTask.setIdTask(Integer.valueOf(idsRs.getString("val")));
                list.add(empTask);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(connection, findIdEntity, findIdCustomer);

        }

        return list;
    }

    @Override
    public boolean delete(EmpTask empTask) {
        Connection connection = null;
        boolean deleted = false;
        PreparedStatement findIdEntity1 = null;
        PreparedStatement delEntity = null;
        PreparedStatement delVals = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            findIdEntity1 = connection.prepareStatement("SELECT id_entity FROM \"ENTITY_VAL\" WHERE val=? AND id_attr=?");
            findIdEntity1.setString(1,String.valueOf(empTask.getIdTask()));
            findIdEntity1.setInt(2,30);
            ResultSet idEntityRs1 = findIdEntity1.executeQuery();
            List<Integer> list1 = new ArrayList<>();
             while (idEntityRs1.next()){
                 list1.add(idEntityRs1.getInt("id_entity"));
             }
            List<Integer> list2 = new ArrayList<>();
            findIdEntity1.setString(1,String.valueOf(empTask.getIdEmp()));
            findIdEntity1.setInt(2,29);
            idEntityRs1 = findIdEntity1.executeQuery();
            while (idEntityRs1.next()){
                list2.add(idEntityRs1.getInt("id_entity"));
            }
            int id = 0;
            for (Integer i1:list1){
                for (Integer i2:list2){
                    if (i2.equals(i1)){
                        id=i2;
                    }
                }
            }
            if (id>0) {

                delEntity = connection.prepareStatement("DELETE FROM \"ENTITY\" WHERE id=?");
                delEntity.setInt(1, id);

                delVals = connection.prepareStatement("DELETE FROM \"ENTITY_VAL\" WHERE id_entity=?");
                delVals.setInt(1, id);
            }
            if ((delEntity.executeUpdate() > 0) && (delVals.executeUpdate() > 0)) {
                deleted = true;
            }
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
            closeQuietly(connection,findIdEntity1, delEntity, delVals);
        }
        return deleted;
    }
}
