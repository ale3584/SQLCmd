package ua.com.juja.sqlcmd.controller;

import com.sun.javaws.exceptions.ExitException;
import com.sun.javaws.jnl.InformationDesc;
import ua.com.juja.sqlcmd.controller.commads.Command;
import ua.com.juja.sqlcmd.controller.commads.Connect;
import ua.com.juja.sqlcmd.controller.commads.Help;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.utils.InputUtils;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by indigo on 25.08.2015.
 */
public class MainController {

    private DatabaseManager manager;
    private View view;
    private List<Command> commands;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new ArrayList<>(Arrays.asList(
                new Connect(manager, view),
                new Help(view)
               /* new CreateDatabase(manager, view),
                new Databases(manager, view),
                new DropDatabase(manager, view),
                new Exit(view),
                new Help(view),
                new IsConnected(manager, view),
                new Clear(manager, view),
                new CreateEntry(manager, view),
                new CreateTable(manager, view),
                new Disconnect(manager, view),
                new DropTable(manager, view),
                new Find(manager, view),
                new Tables(manager, view),
                new Update(manager, view),
                new Unsupported(view)*/
        ));
    }

    public void run() {
        connectToDb();

        while (true) {
            view.write("Введи команду (или help для помощи):");
            InputUtils enter = new InputUtils(view.read());
            runCommand(enter);
        }
    }

    private void doFind(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        String[] tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);

        DataSet[] tableData = manager.getTableData(tableName);
        printTable(tableData);
    }

    private void printTable(DataSet[] tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("--------------------");
        view.write(result);
        view.write("--------------------");
    }

    private void doList() {
        String[] tableNames = manager.getTableNames();

        String message = Arrays.toString(tableNames);

        view.write(message);
    }

    private void connectToDb() {
        view.write("Привет юзер!");
        view.write("Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: database|userName|password");

        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("\\|");
                if (data.length != 3) {
                    throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 3, но есть: " + data.length);
                }
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];

                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                printError(e);
            }
        }

        view.write("Успех!");
    }

    private void printError(Exception e) {
        String message = /*e.getClass().getSimpleName() + ": " + */ e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + /*cause.getClass().getSimpleName() + ": " + */ cause.getMessage();
        }
        view.write("Неудача! по причине: " + message);
        view.write("Повтори попытку.");
    }


    private void runCommand(InputUtils enter) {
        for (Command command : commands) {
            try {
                if (command.is(enter)) {
                    command.process(enter);
                    break;
                }
            } catch (Exception e) {
                if (e instanceof ExitException) {
                    throw e;
                }
                printError(e);
            }
        }
    }
}
