package com.java.parawisata.javaparawisata.Utils.Database;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

public class DBConnection {
    private static Connection connection;

    public static Connection GetConnection() {
        try {
            String urlString =
                    String.format("jdbc:sqlserver://%s;encrypt=true;trustServerCertificate=true;database=%s;user=%s;password=%s",
                            "localhost:1433", "SQL_JavaParawisata","user_po", "Testing1");

            DriverManager.registerDriver(new SQLServerDriver());
            connection = DriverManager.getConnection(urlString);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            connection = null;
        }
        return connection;
    }

    public static Boolean ExecuteQuery(String query) {
        Boolean response = false;
        try {
            connection = GetConnection();
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement(
            );

            response = statement.execute(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
    public static List<List<Object>> MappingStatement(boolean result, Statement statement, List<Class> listMapping) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<List<Object>> response = new ArrayList<>();
        try {
            int classCounter = 0;
            do {
                if (result) {
                    ResultSet resultSet = statement.getResultSet();
                    response.add(MappingResultSet(resultSet, listMapping.get(classCounter)));
                    classCounter++;
                }
                result = statement.getMoreResults();
            } while (result) ;
        } catch (Exception ex) {
            throw ex;
        }
        return response;
    }
    public static List<List<Object>> ExecuteQuery(String query, List<Class> listClassMapping) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<List<Object>> response = new ArrayList<>();
        try {
            connection = GetConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement(
            );

            boolean result = statement.execute(query);
            int classCounter = 0;
            do {
                if (result) {
                    ResultSet resultSet = statement.getResultSet();
                    response.add(MappingResultSet(resultSet, listClassMapping.get(classCounter)));
                    classCounter++;
                }
                result = statement.getMoreResults();
            } while (result);
        } catch (Exception ex) {
            throw ex;
        }
        return response;
    }
    public static List<List<Object>> ExecuteQuery(String query, Map<String, Object> listParameter, List<Class> listClassMapping) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<List<Object>> response = new ArrayList<>();
        try {
            // <editor-fold desc="replace query">
            for (Map.Entry<String, Object> entry : listParameter.entrySet()) {
                query = query.replace(entry.getKey(), entry.getValue().toString());
            }
            // </editor-fold>

            response = ExecuteQuery(query, listClassMapping);
        } catch (Exception ex) {
            throw ex;
        }
        return response;
    }
    public static <T> List<T> MappingResultSet(ResultSet resultSet, Class<T> type) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Field> fields = Arrays.asList(type.getDeclaredFields());
        fields.forEach(x -> x.setAccessible(true));

        List<T> response = new ArrayList<>();
        while (resultSet.next()) {
            T dto = type.getConstructor().newInstance();
            for (Field x : fields) {
                String name = x.getName();
                try {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        if (metaData.getColumnName(i).equalsIgnoreCase(name)) {
                            String value = resultSet.getString(name) == null ? "" : resultSet.getString(name);
                            x.set(dto, x.getType().getConstructor(String.class).newInstance(value));
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            response.add(dto);
        }
        return response;
    }
}
