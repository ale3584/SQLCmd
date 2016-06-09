package ua.com.juja.sqlcmd.controller.commads;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

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
        //do nothing
    }

    public abstract String description();

    public abstract String format();

}
