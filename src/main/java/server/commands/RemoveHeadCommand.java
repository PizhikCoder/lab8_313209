package server.commands;

import server.core.Invoker;
import shared.connection.requests.RemoveModelServerRequest;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import shared.core.models.MusicBand;
import shared.interfaces.IDataManipulationCommand;

/**
 * The class contains an implementation of the remove_head command
 */
public class RemoveHeadCommand extends Command implements IDataManipulationCommand {

    public RemoveHeadCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(int owner_id, Object arguments) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (invoker.getModelsManager().getModels().isEmpty()) {
            invoker.getPrinter().print("Models collection is empty!");
        }
        MusicBand musicBand = invoker.getModelsManager().getModels().getFirst();
        if (musicBand.getOwnerId() != owner_id) {
            invoker.getPrinter().print("The target model belongs to another user.");
            return "";
        }
        invoker.getPrinter().print(musicBand.toString());
        long id = musicBand.getId();
        if (invoker.getDatabaseHandler().removeModel(id)) {
            invoker.getConnection().sendChangesToAll(new RemoveModelServerRequest(musicBand));
            invoker.getModelsManager().removeById(invoker.getModelsManager().getModels().getFirst().getId(), invoker.getPrinter());
            return "Remove head command executed!";
        }
        invoker.getPrinter().print( "Model was not removed.");
        return "";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"remove_head\": This command shows the first model in the collection and than removes it.");
    }
}
