package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.TextTable;
import ua.com.juja.alexander.sqlcmd.view.View;

import java.util.Map;

/**
 * Created by ALEXANDER on 21.09.2016.
 */
public class Tables extends Command {

    public Tables(DatabaseManager manager, View view) {
        super(manager,view);
    }

    public Tables() {

    }

    @Override
    public String description() {
        return "Выводит список таблиц базы данных.";
    }

    @Override
    public String format() {
        return "tables";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        Map<String,Object> tableNames = manager.getTableNames();
        TextTable textTable = new TextTable(1);
        textTable.addCell("Имя таблицы");
        textTable.addCells(tableNames);
        view.write(textTable.getTable());
    }
}
