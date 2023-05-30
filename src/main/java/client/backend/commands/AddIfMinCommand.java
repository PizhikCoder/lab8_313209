package client.backend.commands;


import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.connection.ThreadsBridgeHandler;
import client.backend.core.Invoker;
import shared.commands.commandsdtos.CommandDTO;
import shared.commands.enums.DataField;
import shared.connection.requests.CommandRequest;
import shared.connection.requests.ValidationRequest;

import java.util.Map;

/**
 * The class contains an implementation of the add_if_min command.
 */
public class AddIfMinCommand extends Command  {
    private final Invoker invoker;
    private final long id;
    private final Map<DataField, Object> data;

    public AddIfMinCommand(Invoker invoker, long id, Map<DataField, Object> data) {
        this.invoker = invoker;
        this.id = id;
        this.data = data;
    }

    @Override
    public boolean execute(String... args) {
        invoker.getConnection().getSender().send(new ValidationRequest(new CommandDTO("AddIfMinCommand"), id));
        if (ThreadsBridgeHandler.getValidationResponse(invoker.getPipedInputStream(), invoker.getPrinter())){
            invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("AddIfMinCommand"), new Object[]{data, id}));
        }
        if(ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter())){
            invoker.getPrinter().print(CommandsAnswers.ADD_IF_MIN_COMMAND_EXECUTED.toString());
            return true;
        }
        invoker.getPrinter().print(CommandsAnswers.ADD_IF_MIN_COMMAND_NOT_EXECUTED.toString());
        return false;
    }
}
