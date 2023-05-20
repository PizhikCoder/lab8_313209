package fxproject.backend.commands;


import fxproject.backend.core.Invoker;
import shared.core.ClientInfo;

public class LogOutCommand extends Command {
    private Invoker invoker;

    public LogOutCommand(Invoker invoker){
        this.invoker = invoker;
    }

    @Override
    public boolean execute(String... args) {
        ClientInfo.setIsAuthorized(false);
        ClientInfo.setLogin("");
        ClientInfo.setPassword("");
        return true;
    }
}
