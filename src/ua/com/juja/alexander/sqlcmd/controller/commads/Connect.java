package ua.com.juja.alexander.sqlcmd.controller.commads;


import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.model.DatabaseManagerException;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 14.08.2016.
 */
public class Connect extends Command {

    public Connect(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public Connect() {

    }

    @Override
    public String description() {
        return "подключение к базе данных";
    }

    @Override
    public String format() {
        return "connect|database";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        String[] parameters = userCommand.getParameters();

        try {
            manager.connect(parameters[1], null, null);
        } catch (DatabaseManagerException e) {
            manager.connect("", null, null);
            throw e;
        }

        view.write("Подключение к базе данных произошло успешно");
    }
}
