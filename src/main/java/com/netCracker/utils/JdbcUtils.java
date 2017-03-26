package com.netCracker.utils;

import java.sql.*;

public class JdbcUtils {
    private static final String USER = "aleksandr";
    private static final String PASSWORD = "";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/company?autoReconnect=true&useSSL=false";

    //Deprecated
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {/*NOP*/}
        }
    }

    public static void closeQuietly(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            closeQuietly(resource);
        }
    }

    public static boolean delByIdEntity(String val, int attrId, Connection connection) {
        boolean deleted = false;
        PreparedStatement findIdEntity = null;
        PreparedStatement delEntity = null;
        PreparedStatement delVals = null;
        try {
            connection.setAutoCommit(false);
            findIdEntity = connection.prepareStatement("SELECT id_entity FROM \"ENTITY_VAL\" WHERE val=? AND id_attr=?");
            findIdEntity.setString(1, val);
            findIdEntity.setInt(2, attrId);
            ResultSet idEntityRs = findIdEntity.executeQuery();
            if (idEntityRs.next()) {
                int id = idEntityRs.getInt("id_entity");
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
            closeQuietly(connection, findIdEntity, delEntity, delVals);
        }
        return deleted;
    }
}
