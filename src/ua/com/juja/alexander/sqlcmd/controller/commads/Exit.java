package ua.com.juja.alexander.sqlcmd.controller.commads;



import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;
import ua.com.juja.alexander.sqlcmd.view.View;

/**
 * Created by ALEXANDER on 22.09.2016.
 */
public class Exit extends Command {

    public Exit(View view) {
        super(view);
    }

    public Exit() {

    }

    @Override
    public String description() {
        return "Завершает работу с программой.";
    }

    @Override
    public String format() {
        return "exit";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());
        view.write("Всего доброго!");
        throw new ExitException();
    }
}
