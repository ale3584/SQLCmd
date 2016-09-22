package ua.com.juja.alexander.sqlcmd.model;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public interface DatabaseManager {

    DataSet[] getTableData(String tableName);

    String[] getTableNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableColumns(String tableName);

    String[] getDataBases();

}
