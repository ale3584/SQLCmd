package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 25.09.2016.
 */
public class IsConnected extends Command {
    public IsConnected(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public IsConnected() {
    }


    @Override
    public boolean is(InputUtils command) {
        return !manager.isConnected();
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String format() {
        return null;
    }

    @Override
    public void process(InputUtils userCommand) {
        view.write("Вы не можете использовать команду "+userCommand.toString()+". Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password");
    }
}
