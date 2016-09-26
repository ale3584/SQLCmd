package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DataSet;
import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ALEXANDER on 25.09.2016.
 */
public class CreateEntry extends Command {
    public CreateEntry(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public CreateEntry() {
    }

    @Override
    public String description() {
        return "Создает запись в таблице.";
    }

    @Override
    public String format() {
        return "createEntry|tableName|column1|value1|column2|value2|...|columnN|valueN";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.pairValidation(format());
        String[] parameters = userCommand.getParameters();
        Map<String,Object> tableData = parseData(parameters);
        manager.insert(parameters[1], tableData);
        view.write(String.format("Запись в таблице '%s' успешно создана: %s", parameters[1], tableData));
    }

    private Map<String,Object> parseData(String[] splitData) {
        Map<String,Object> result = new LinkedHashMap<String, Object>();
        for (int index = 0; index < (splitData.length / 2); index++) {
            String column = splitData[index * 2];
            String value = splitData[index * 2 + 1];
            result.put(column, value);
        }
        return result;
    }
}
