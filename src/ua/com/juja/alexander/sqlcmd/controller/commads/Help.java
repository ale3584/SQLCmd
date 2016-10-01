package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.view.View;
import ua.com.juja.alexander.sqlcmd.controller.commads.utils.InputUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ALEXANDER on 14.08.2016.
 */
public class Help extends Command {

    private List<Command> commands;

    public Help(View view) {
        super(view);
        this.commands = new ArrayList<>(Arrays.asList(
                new Connect(),
                this,
                new Exit(),
                new Find(),
                new Clear(),
                new Databases(),
                new CreateDatabase(),
                new DropDatabase(),
                new Tables(),
                new CreateTable(),
                new DropTable(),
                new CreateEntry(),
                new Update(),
                new Disconnect()
        ));
    }

    @Override
    public String description() {
        return "список существующих команд";
    }

    @Override
    public String format() {
        return "help";
    }

    @Override
    public void process(InputUtils userCommand) {
        userCommand.parametersValidation(format());

        view.write("Существующие команды:");
        for (Command command: commands) {
            view.write("\t\t" + command.format() + " -- " + command.description());
        }
    }
}
