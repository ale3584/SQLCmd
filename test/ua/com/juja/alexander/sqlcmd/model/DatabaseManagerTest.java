package ua.com.juja.alexander.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public abstract class DatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = getDatabaseManager();
        manager.connect("test", "root", "162399");
    }

    public abstract DatabaseManager getDatabaseManager();

    @Test
    public void testGetAllTableNames() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[users]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("users");

        // when
        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("pass", "pass");
        input.put("id", 13);
        manager.create("users", input);

        // then
        DataSet[] users = manager.getTableData("users");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, id, pass]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, 13, pass]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("pass", "pass");
        input.put("id", 13);
        manager.create("users", input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("pass", "pass2");
        newValue.put("name", "Pup");
        manager.update("users", 13, newValue);

        // then
        DataSet[] users = manager.getTableData("users");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, id, pass]", Arrays.toString(user.getNames()));
        assertEquals("[Pup, 13, pass2]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testGetColumnNames() {
        // given
        manager.clear("users");

        // when
        String[] columnNames = manager.getTableColumns("users");

        // then
        assertEquals("[name, id, pass]", Arrays.toString(columnNames));
    }
}
