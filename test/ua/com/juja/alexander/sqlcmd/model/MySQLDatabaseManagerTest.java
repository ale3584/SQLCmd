package ua.com.juja.alexander.sqlcmd.model;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public class MySQLDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new MySQLDatabaseManager();
    }
}
