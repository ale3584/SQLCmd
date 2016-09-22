package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 22.09.2016.
 */
public class DropDatabase extends Command {
    public DropDatabase(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public DropDatabase() {

    }

    @Override
    public String description() {
        return "Удаляет базу данных.";
    }

    @Override
    public String format() {
        return "dropdatabase|databasename";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        String[] parameters = userCommand.getParameters();

        manager.dropDatabase(parameters[1]);
        view.write("База данных '" + parameters[1] + "' успешно  удалена.");

    }
}
