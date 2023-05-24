package server.commands;

import server.core.Invoker;

public class LogOutCommand extends Command{
    public LogOutCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public String getCommandInfo() {
        return "Command \"log_out\": De-authorize client.";
    }
}
