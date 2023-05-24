package server.commands;

import server.core.Invoker;

public class RegisterCommand extends Command{
    public RegisterCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public String getCommandInfo() {
        return "Command \"register\": Registers a new client.";
    }
}
