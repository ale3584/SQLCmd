package ua.com.juja.alexander.sqlcmd.controller;

import com.sun.javaws.exceptions.ExitException;
import ua.com.juja.alexander.sqlcmd.controller.commads.*;
import ua.com.juja.alexander.sqlcmd.model.DataSet;
import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public class MainController {

    private DatabaseManager manager;
    private View view;
    private List<Command> commands;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new ArrayList<>(Arrays.asList(
                new Help(view),
                new Connect(manager, view),
                new Find(manager, view),
                new Clear(manager, view),
                new Tables(manager, view),
                new Databases(manager, view),
                new CreateDatabase(manager, view),
                new DropDatabase(manager, view),
                new DropTable(manager, view),
                new IsConnected(manager, view),
                new CreateEntry(manager, view),
                new CreateTable(manager, view),
                new Disconnect(manager, view),
                new Update(manager, view),
                new Exit(view),
                new Unsupported(view)
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
        String message = e.getClass().getSimpleName() + ": " +  e.getMessage();
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
