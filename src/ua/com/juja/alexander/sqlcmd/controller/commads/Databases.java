package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.utils.TextTable;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 22.09.2016.
 */
public class Databases extends Command {
    public Databases(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public String description() {
        return "Выводит список баз данных.";
    }

    @Override
    public String format() {
        return "databases";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());

        TextTable textTable = new TextTable(1);
        textTable.addCell("Имя базы данных");
        textTable.addCells(manager.getDataBases());
        view.write(textTable.getTable());
    }
}
