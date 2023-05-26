package client.backend.commands;

import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.connection.ThreadsBridgeHandler;
import client.backend.core.Invoker;
import shared.commands.commandsdtos.CommandDTO;
import shared.connection.requests.CommandRequest;
import shared.core.exceptions.*;

public class ClearCommand extends Command{

    private Invoker invoker;

    public ClearCommand(Invoker invoker){
        this.invoker = invoker;
    }

    @Override
    public boolean execute(String... args)  {
        invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("ClearCommand"), null));
        if(ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter())){
            invoker.getPrinter().print(CommandsAnswers.CLEAR_COMMAND_EXECUTED.toString());
            return true;
        }
        return false;
    }
}
