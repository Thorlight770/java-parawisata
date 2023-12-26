package com.java.parawisata.javaparawisata.Utils.Database;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

import static java.time.LocalDate.MIN;

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
                if (listMapping.size() == classCounter) break;
                else result = statement.getMoreResults();
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
                            if (x.getType().isNestmateOf(String.class)) {
                                String value = resultSet.getString(name) == null ? "" : resultSet.getString(name);
                                x.set(dto, x.getType().getConstructor(String.class).newInstance(value));
                            }
                            else if (x.getType().isNestmateOf(long.class) || x.getType().isNestmateOf(Long.class)) {
                                long value = resultSet.getLong(name) == 0 ? 0 : resultSet.getLong(name);
                                x.set(dto, x.getType().getConstructor(long.class).newInstance(value));
                            }
                            else if (x.getType().isNestmateOf(int.class) || x.getType().isNestmateOf(Integer.class))
                                x.set(dto, x.getType().getConstructor(int.class).newInstance(resultSet.getInt(name)));
                            else if (x.getType().isNestmateOf(java.sql.Date.class) || x.getType().isNestmateOf(java.util.Date.class) || x.getType().isNestmateOf(LocalDate.class)) {
                                Date value = resultSet.getDate(name) == null ? Date.valueOf(MIN) : resultSet.getDate(name);
                                if (!value.equals(Date.valueOf(MIN))) x.set(dto, value);
                            }
                            else if (x.getType().isNestmateOf(Double.class))
                                x.set(dto, x.getType().getConstructor(double.class).newInstance(resultSet.getDouble(name)));
                            else if (x.getType().isNestmateOf(boolean.class) || x.getType().isNestmateOf(Boolean.class))
                                x.set(dto, x.getType().getConstructor(boolean.class).newInstance(resultSet.getBoolean(name)));
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
    public static <T> SQLServerDataTable MappingListToTable(List<T> list) throws SQLServerException {
        SQLServerDataTable response = new SQLServerDataTable();
        List<Field> fields = List.of(list.get(0).getClass().getDeclaredFields());
        fields.forEach(x -> x.setAccessible(true));
        for (Field field: fields) {
            if (field.getType().isNestmateOf(String.class))
                response.addColumnMetadata(field.getName(), Types.VARCHAR);
            else if (field.getType().isNestmateOf(long.class) || field.getType().isNestmateOf(Long.class))
                response.addColumnMetadata(field.getName(), Types.BIGINT);
            else if (field.getType().isNestmateOf(int.class) || field.getType().isNestmateOf(Integer.class))
                response.addColumnMetadata(field.getName(), Types.INTEGER);
            else if (field.getType().isNestmateOf(double.class) || field.getType().isNestmateOf(Double.class))
                response.addColumnMetadata(field.getName(), Types.DECIMAL);
            else if (field.getType().isNestmateOf(Date.class) || field.getType().isNestmateOf(java.util.Date.class))
                response.addColumnMetadata(field.getName(), Types.DATE);
            else if (field.getType().isNestmateOf(boolean.class) || field.getType().isNestmateOf(Boolean.class))
                response.addColumnMetadata(field.getName(), Types.BOOLEAN);
        }

        return response;
    }
}
