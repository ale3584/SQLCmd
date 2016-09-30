package ua.com.juja.alexander.sqlcmd.model;

import java.util.Map;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public interface DatabaseManager {

    Map<String,Object> getTableData(String tableName);

    Map<String,Object> getTableNames();

    void createDatabase(String databaseName);

    void dropDatabase(String databaseName);

    void dropTable(String databaseName);

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void create(String tableName, Map<String,Object> input);

    void update(String tableName, int id, Map<String,Object> newValue);

    Map<String,Object> getTableColumns(String tableName);

    Map<String,Object> getDataBases();

    void disconnectFromDatabase();

    void insert(String parameter, Map<String,Object> tableData);

    void createTable(String parameter);

    boolean isConnected();
}
