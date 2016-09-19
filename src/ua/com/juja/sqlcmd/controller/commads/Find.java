package ua.com.juja.sqlcmd.controller.commads;

import ua.com.juja.sqlcmd.utils.InputUtils;

/**
 * Created by ALEXANDER on 14.08.2016.
 */
public class Find extends Command {

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

    }
}
