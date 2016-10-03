package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 25.09.2016.
 */
public class CreateTable extends Command{

    public CreateTable() {
    }

    public CreateTable(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public String description() {
        return "Создает таблицу в базе данных, в скобках необходимо ввести описание полей в формате SQL\n" +
                "Пример: user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE,password varchar(225))";
    }

    @Override
    public String format() {
        return "createTable|tableName(column1,column2,...,columnN)";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        String[] parameters = userCommand.getParameters();
        manager.createTable(parameters[1]);
        view.write("Таблица успешно создана.");
    }
}
