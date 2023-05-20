package server.commands;

import server.core.Invoker;

/**
 * The class contains an implementation of the save command
 */
public class ExitCommand extends Command {
    public ExitCommand(Invoker invoker) {
        super(invoker);
    }


    @Override
    public String getCommandInfo() {
        return "Command \"exit\": This command terminates the programme by prompting you to save the changes.";
    }
}
