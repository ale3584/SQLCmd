package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ALEXANDER on 25.09.2016.
 */
public class Update extends Command {

    public Update(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public Update() {
    }

    @Override
    public String description() {
        return "Обновляет запись в таблице, используя ID";
    }

    @Override
    public String format() {
        return "update|tableName|ID";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        String[] parameters = userCommand.getParameters();

        view.write("Введите данные в формате 'column1|value1|column2|value2|...|columnN|valueN':");
        InputUtils data = new InputUtils(view.read());
        data.pairValidation("column1|value1|column2|value2|...|columnN|valueN");
        String[] dataParameters = data.getParameters();

        Map<String,Object> newTableData = parseData(dataParameters);
        int id = Integer.parseInt(parameters[2]);
        manager.update(parameters[1], id, newTableData);

        view.write("Данные успешно обновлены.");
    }


    private Map<String,Object> parseData(String[] splitData) {
        Map<String,Object> result = new LinkedHashMap<String,Object>();
        for (int index = 0; index < (splitData.length / 2); index++) {
            String column = splitData[index * 2];
            String value = splitData[index * 2 + 1];

            result.put(column, value);
        }
        return result;
    }
}
