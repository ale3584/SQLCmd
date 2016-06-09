package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.controller.MainController;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.MySQLDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.sql.*;

/**
 * Created by indigo on 21.08.2015.
 */
public class Main {
    public static void main(String[] argv) throws ClassNotFoundException, SQLException {
        DatabaseManager manager = new MySQLDatabaseManager();
        View view = new Console();
        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
