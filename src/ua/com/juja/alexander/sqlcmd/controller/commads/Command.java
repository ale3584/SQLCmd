package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.view.View;
import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public abstract class Command {
    protected DatabaseManager manager;
    protected View view;

    public Command(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    public Command(View view) {
        this.view = view;
    }

    public Command() {

    }

    public boolean is(InputUtils command) {
        String[] splitFormat = format().split("\\|");
        String[] parameters = command.getParameters();
        return parameters[0].equals(splitFormat[0]);
    }

    public abstract String description();

    public abstract String format();

    public abstract void process(InputUtils userCommand);

}
