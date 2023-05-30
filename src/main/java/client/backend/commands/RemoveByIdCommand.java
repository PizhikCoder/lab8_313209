package client.backend.commands;


import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.connection.ThreadsBridgeHandler;
import client.backend.core.Invoker;
import client.backend.core.MusicBandFieldsValidators;
import shared.commands.commandsdtos.CommandDTO;
import shared.connection.requests.CommandRequest;
import shared.core.exceptions.CommandParamsException;

/**
 * The class contains an implementation of the remove_by_id command
 */
public class RemoveByIdCommand extends Command{
    private final Invoker invoker;

    private final int ID_INDEX = 0;

    private final int EXPECTED_ARGUMENTS_COUNT = 1;

    public RemoveByIdCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public boolean execute(String... args) throws CommandParamsException {
        if (args.length != EXPECTED_ARGUMENTS_COUNT){
            throw new CommandParamsException(args.length, EXPECTED_ARGUMENTS_COUNT);
        }
        if (MusicBandFieldsValidators.bandIdValidate(args[ID_INDEX])){
            long id = Long.parseLong(args[ID_INDEX]);
            invoker.getConnection().getSender().send(new CommandRequest(new CommandDTO("RemoveByIdCommand"), id));
            if(ThreadsBridgeHandler.waitCommandExecuted(invoker.getPipedInputStream(), invoker.getPrinter())){
                invoker.getPrinter().print(CommandsAnswers.REMOVE_BY_ID_COMMAND_EXECUTED.toString());
                return true;
            }
        }
        invoker.getPrinter().print(CommandsAnswers.REMOVE_BY_ID_COMMAND_NOT_EXECUTED.toString());
        return false;
    }
}
