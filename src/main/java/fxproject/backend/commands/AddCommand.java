package fxproject.backend.commands;

import fxproject.backend.connection.ThreadsBridgeHandler;
import fxproject.backend.core.Invoker;
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
        return ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter());
    }

}
