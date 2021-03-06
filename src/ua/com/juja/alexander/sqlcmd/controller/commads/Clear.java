package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.view.View;
import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;

/**
 * Created by ALEXANDER on 19.06.2016.
 */
public class Clear extends Command {

    public Clear(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public Clear() {

    }

    @Override
    public String description() {
        return "очистка таблицы";
    }

    @Override
    public String format() {
        return "clear|tableName";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        String[] parameters = userCommand.getParameters();

        view.write("Вы действительно хотите очистить таблицу? Y/N");
        if ("Y".equalsIgnoreCase(view.read())) {
            manager.clear(parameters[1]);
            view.write(String.format("таблица '%s' успешно очищена", parameters[1]));
        }
    }
}
