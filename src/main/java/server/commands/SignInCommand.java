package server.commands;

import server.core.Invoker;

public class SignInCommand extends Command{
    public SignInCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public String getCommandInfo() {
        return "Command \"auth\": Authorize client.";
    }
}
