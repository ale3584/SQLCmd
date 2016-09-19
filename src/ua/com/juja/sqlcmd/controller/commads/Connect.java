package ua.com.juja.sqlcmd.controller.commads;


import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.DatabaseManagerException;
import ua.com.juja.sqlcmd.utils.InputUtils;
import ua.com.juja.sqlcmd.view.View;

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
