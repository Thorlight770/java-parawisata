package com.java.parawisata.javaparawisata.Utils.Database;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class DBConnection {
    private static Connection connection;

    public static Connection GetConnection() {
        try {
            String urlString =
                    String.format("jdbc:sqlserver://%s;database=%s;user=%s;password=%s",
                            "KELAS-MUHAMMADG", "user_po", "Testing1");

            DriverManager.registerDriver(new SQLServerDriver());
            connection = DriverManager.getConnection(urlString);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            connection = null;
        }
        return connection;
    }

    public static ResultSet ExecuteQuery(String query) throws SQLException {
        ResultSet response = null;
        try {
            connection = GetConnection();
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            statement.close();
        } catch (Exception ex) {
            connection.rollback();
        } finally {
            connection.close();
        }
        return response;
    }

    public static <T> List<T> MappingResultSet(ResultSet resultSet, Class<T> type) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Field> fields = Arrays.asList(type.getDeclaredFields());
        fields.forEach(x -> x.setAccessible(true));

        List<T> response = new ArrayList<>();
        while (resultSet.next()) {
            T dto = type.getConstructor().newInstance();
            fields.forEach(x -> {
                String name = x.getName();
                try {
                    String value = resultSet.getString(name);
                    x.set(dto, x.getType().getConstructor(String.class).newInstance(value));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            response.add(dto);
        }
        return response;
    }
}
