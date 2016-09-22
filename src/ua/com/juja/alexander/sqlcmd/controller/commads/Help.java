package ua.com.juja.alexander.sqlcmd.controller.commads;

import ua.com.juja.alexander.sqlcmd.view.View;
import ua.com.juja.alexander.sqlcmd.utils.InputUtils;

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
                new Connect(manager, view),
                this,
                new Find(manager, view),
                new Clear(manager, view),
                new Tables(manager, view),
                new Exit(view),
                new Databases(manager,view),
                new CreateDatabase(manager,view),
                new DropDatabase(manager,view),
                new DropTable(manager,view)
                /*new CreateEntry(),
                new CreateTable(),
                new Disconnect(),

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
