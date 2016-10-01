package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 22.09.2016.
 */
public class CreateDatabase extends Command{
    public CreateDatabase(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public CreateDatabase() {

    }

    @Override
    public String description() {
        return "Создает новую базу данных.";
    }

    @Override
    public String format() {
        return "createDatabase|databaseName";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        String[] parameters = userCommand.getParameters();
        manager.createDatabase(parameters[1]);
        view.write("База данных '" + parameters[1] + "' успешно  создана.");
    }
}
