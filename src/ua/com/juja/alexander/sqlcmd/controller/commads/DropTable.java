package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 22.09.2016.
 */
public class DropTable extends Command {
    public DropTable(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public DropTable() {

    }

    @Override
    public String description() {
        return "Удаляет таблицу базы данных.";
    }

    @Override
    public String format() {
        return "droptable|tablename";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        String[] parameters = userCommand.getParameters();
        manager.dropTable(parameters[1]);
        view.write("Таблица '" + parameters[1] + "' успешно  удалена.");
    }
}
