package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 25.09.2016.
 */
public class Disconnect extends Command {

    public Disconnect(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public Disconnect() {
    }

    @Override
    public String description() {
        return "Отключение от текущей базы данных.";
    }

    @Override
    public String format() {
        return "disconnect";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        manager.disconnectFromDatabase();
        view.write("Подключение к БД разорвано.");


    }
}
