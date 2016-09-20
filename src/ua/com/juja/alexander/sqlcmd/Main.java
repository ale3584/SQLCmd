package ua.com.juja.alexander.sqlcmd;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.view.Console;
import ua.com.juja.alexander.sqlcmd.view.View;
import ua.com.juja.alexander.sqlcmd.controller.MainController;
import ua.com.juja.alexander.sqlcmd.model.MySQLDatabaseManager;

import java.sql.*;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public class Main {
    public static void main(String[] argv) throws ClassNotFoundException, SQLException {
        DatabaseManager manager = new MySQLDatabaseManager();
        View view = new Console();
        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
