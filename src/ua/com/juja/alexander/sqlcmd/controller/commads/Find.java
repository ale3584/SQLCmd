package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DataSet;
import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.utils.*;
import ua.com.juja.alexander.sqlcmd.view.View;

import java.util.Map;

public class Find extends Command {

    public Find(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public Find() {

    }

    @Override
    public String description() {
        return "поиск данных в таблице";
    }

    @Override
    public String format() {
        return "find|tableName";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        String[] parameters = userCommand.getParameters();
        String tableName = parameters[1];
        Map<String,Object> tableColumns = manager.getTableColumns(tableName);
        Map<String,Object> tableData = manager.getTableData(tableName);
        view.write(printTable(tableColumns,tableData));
    }

    private String printTable(Map<String,Object> tableColumns, Map<String,Object> tableData){
        TextTable textTable = new TextTable(tableColumns.size());
        textTable.addCells(tableColumns);
        textTable.addCells(tableData);
        return textTable.getTable();
    }
}
