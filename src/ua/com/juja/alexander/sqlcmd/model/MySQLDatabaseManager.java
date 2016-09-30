package ua.com.juja.alexander.sqlcmd.model;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public class MySQLDatabaseManager implements DatabaseManager {

    private static final String ERROR = "It is impossible because: ";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";

    private boolean isConnected;
    private String user;
    private String password;

    private Connection connection;
    private String DataBaseName;

    @Override
    public Map<String,Object> getTableData(String tableName) {
        Map<String,Object> result = new LinkedHashMap<String, Object>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + DataBaseName + "." + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    result.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public Map<String,Object> getTableNames() {
        Map<String,Object> tables = new LinkedHashMap<String,Object>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema='" + DataBaseName + "' AND table_type='BASE TABLE'");
            while (rs.next()) {
                tables.put(rs.getString("table_name"),rs.getString("table_name"));
            }
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }

    @Override
    public void createDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            throw new DatabaseManagerException(ERROR, e);
        }
    }

    @Override
    public void dropDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP DATABASE IF EXISTS" + databaseName);
        } catch (SQLException e) {
            throw new DatabaseManagerException(ERROR, e);
        }
    }

    @Override
    public void dropTable(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        } catch (SQLException e) {
            throw new DatabaseManagerException(ERROR, e);
        }
    }

    @Override
    public void connect(String database, String userName, String password) {
        if (userName != null && password != null) {
            this.user = userName;
            this.password = password;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + ":" + PORT + "/" + database, userName,
                    password);
            DataBaseName = database;
            isConnected = true;
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s",
                            database, userName),
                    e);
        }
    }

    @Override
    public void clear(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM " + tableName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String tableName, Map<String,Object> input) {
        try {
            Statement stmt = connection.createStatement();

            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate("INSERT INTO " + tableName + " (" + tableNames + ")" +
                    "VALUES (" + values + ")");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getValuesFormated(Map<String,Object> input, String format) {
        String values = "";
        for (Object value : input.values()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override
    public void update(String tableName, int id, Map<String,Object> newValue) {
        try {
            String tableNames = getNameFormated(newValue, "%s = ?,");

            String sql = "UPDATE " + tableName + " SET " + tableNames + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            int index = 1;
            for (Object value : newValue.values()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String,Object> getTableColumns(String tableName) {
        Map<String,Object>  tables = new LinkedHashMap<String,Object>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema = '" + DataBaseName + "' AND table_name = '" + tableName + "'");
            while (rs.next()) {
                tables.put(rs.getString("column_name"),rs.getString("column_name"));
            }
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }

    private String getNameFormated(Map<String, Object> newValue, String format) {
        String string = "";
        for (String name : newValue.keySet()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    public Map<String,Object> getDataBases(){
        Map<String,Object> dataBases = new LinkedHashMap<String, Object>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SCHEMATA.SCHEMA_NAME as DBName FROM information_schema.SCHEMATA AS SCHEMATA WHERE SCHEMATA.SCHEMA_NAME NOT IN ('information_schema','mysql','performance_schema') GROUP BY SCHEMATA.SCHEMA_NAME");
            while (rs.next()) {
                dataBases.put(rs.getString("DBName"),rs.getString("DBName"));
            }
            rs.close();
            stmt.close();
            return dataBases;
        } catch (SQLException e) {
            e.printStackTrace();
            return dataBases;
        }
    }

    @Override
    public void disconnectFromDatabase() {
        isConnected = false;
        connect("", user, password);
    }

    @Override
    public void insert(String tableName, Map<String,Object> input) {
        String rowNames = getNameFormated(input, "\"%s\",");
        String values = getValuesFormated(input, "'%s',");
        String sql = createString("INSERT INTO ", tableName, " (", rowNames, ") ", "VALUES (", values, ")");

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DatabaseManagerException(ERROR, e);
        }
    }

    @Override
    public void createTable(String parameter) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + parameter);
        } catch (SQLException e) {
            throw new DatabaseManagerException(ERROR, e);
        }
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    private String createString(String... args) {
        StringBuilder result = new StringBuilder();
        for (String arg: args) {
            result.append(arg);
        }
        return result.toString();
    }



}
