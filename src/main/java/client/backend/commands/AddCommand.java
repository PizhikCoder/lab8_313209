package client.backend.commands;

import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.connection.ThreadsBridgeHandler;
import client.backend.core.Invoker;
import shared.commands.commandsdtos.CommandDTO;
import shared.commands.enums.DataField;
import shared.connection.requests.CommandRequest;
import java.util.Map;

/**
 * The class contains an implementation of the add command
 */
public class AddCommand extends Command {
    private final Invoker invoker;
    private final Map<DataField, Object> data;

    public AddCommand(Map<DataField, Object> data, Invoker invoker){
        this.invoker = invoker;
        this.data = data;
    }
    @Override
    public boolean execute(String... arguments) {
        invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("AddCommand"), data));
        if(ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter())){
            invoker.getPrinter().print(CommandsAnswers.ADD_COMMAND_EXECUTED.toString());
            return true;
        }
        invoker.getPrinter().print(CommandsAnswers.ADD_COMMAND_NOT_EXECUTED.toString());
        return false;
    }

}
