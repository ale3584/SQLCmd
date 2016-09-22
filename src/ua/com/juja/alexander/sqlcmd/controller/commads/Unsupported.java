package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 22.09.2016.
 */
public class Unsupported extends Command {
    public Unsupported(View view) {
    }

    @Override
    public boolean is(InputUtils userCommand) {
        return true;
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
        view.write("Команда "+ userCommand.toString()+ " не существует.");
    }
}
