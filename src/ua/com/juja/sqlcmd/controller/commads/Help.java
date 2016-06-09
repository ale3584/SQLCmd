package ua.com.juja.sqlcmd.controller.commads;

import ua.com.juja.sqlcmd.utils.InputUtils;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Help extends Command {

    private List<Command> commands;

    public Help(View view) {
        super(view);
        this.commands = new ArrayList<>(Arrays.asList(
                new Connect(),
                this
                /*new CreateDatabase(),
                new Databases(),
                new Exit(),
                this,
                new Clear(),
                new CreateEntry(),
                new CreateTable(),
                new Disconnect(),
                new DropDatabase(),
                new DropTable(),
                new Find(),
                new Tables(),
                new Update()*/
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
