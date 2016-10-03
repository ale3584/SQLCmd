package ua.com.juja.alexander.sqlcmd.controller;

import ua.com.juja.alexander.sqlcmd.controller.commads.*;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
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
                new Exit(view),
                new Connect(manager, view),
                new IsConnected(manager, view),
                new Find(manager, view),
                new Clear(manager, view),
                new Tables(manager, view),
                new Databases(manager, view),
                new CreateDatabase(manager, view),
                new DropDatabase(manager, view),
                new DropTable(manager, view),
                new CreateEntry(manager, view),
                new CreateTable(manager, view),
                new Disconnect(manager, view),
                new Update(manager, view),
                new Unsupported(view)
        ));
    }

    public void run() {

        try {
            doWork();
        } catch (ExitException e) {
        }
    }

    private void doWork() {
        view.write("Привет юзер!");
        view.write("Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password");

        while (true) {
            InputUtils input = new InputUtils(view.read());
            runCommand(input);
            view.write("Введи команду (или help для помощи):");
        }
    }

    private void printError(Exception e) {
        String message = e.getClass().getSimpleName() + ": " +  e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + cause.getMessage();
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
                break;
            }
        }
    }
}
