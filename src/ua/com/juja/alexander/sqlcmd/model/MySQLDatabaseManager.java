package ua.com.juja.alexander.sqlcmd.model;

import java.sql.*;
import java.util.Arrays;

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
    public DataSet[] getTableData(String tableName) {
        try {
            int size = getSize(tableName);

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM test." + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataSet[0];
        }
    }

    private int getSize(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM test." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    @Override
    public String[] getTableNames() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema='test' AND table_type='BASE TABLE'");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
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
                    "jdbc:mysql://"+HOST+":"+PORT+"/" + database, userName,
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
    public void create(String tableName, DataSet input) {
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

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value: input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        try {
            String tableNames = getNameFormated(newValue, "%s = ?,");

            String sql = "UPDATE " + tableName + " SET " + tableNames + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            int index = 1;
            for (Object value : newValue.getValues()) {
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
    public String[] getTableColumns(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema = "+DataBaseName+" AND table_name = '" + tableName + "'");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("column_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }
}