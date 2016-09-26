package ua.com.juja.alexander.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        Map<String,Object> tableNames = manager.getTableNames();
        assertEquals("[users]", tableNames.values().toString());
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("users");

        // when
        Map<String,Object> input = new LinkedHashMap<String,Object>();
        input.put("name", "Stiven");
        input.put("pass", "pass");
        input.put("id", 13);
        manager.create("users", input);

        // then
        Map<String,Object> users = manager.getTableData("users");
        assertEquals(3, users.size());
        assertEquals("[name, id, pass]", users.keySet().toString());
        assertEquals("[Stiven, 13, pass]", users.values().toString());
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("users");

        Map<String,Object> input = new LinkedHashMap<String, Object>();
        input.put("name", "Stiven");
        input.put("pass", "pass");
        input.put("id", 13);
        manager.create("users", input);

        // when
        Map<String,Object> newValue = new LinkedHashMap<String,Object>();
        newValue.put("pass", "pass2");
        newValue.put("name", "Pup");
        manager.update("users", 13, newValue);

        // then
        Map<String,Object> users = manager.getTableData("users");
        assertEquals(3, users.size());
        assertEquals("[name, id, pass]", users.keySet().toString());
        assertEquals("[Pup, 13, pass2]", users.values().toString());
    }

    @Test
    public void testGetColumnNames() {
        // given
        manager.clear("users");

        // when
        Map<String,Object> columnNames = manager.getTableColumns("users");

        // then
        assertEquals("[name, id, pass]", columnNames.keySet().toString());
    }
}
