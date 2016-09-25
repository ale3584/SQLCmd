package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
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
    public String description() {
        return null;
    }

    @Override
    public String format() {
        return null;
    }

    @Override
    public void process(InputUtils userCommand) {

    }
}
