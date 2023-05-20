package server.commands;

import server.core.Invoker;
import shared.connection.requests.AddModelServerRequest;
import shared.connection.requests.RemoveModelServerRequest;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import shared.interfaces.IDataManipulationCommand;

/**
 * The class contains an implementation of the remove_first command
 */
public class RemoveFirstCommand extends Command implements IDataManipulationCommand {

    public RemoveFirstCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(int owner_id, Object arguments) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        if (invoker.getModelsManager().getModels().isEmpty()){
            invoker.getPrinter().print( "Models collection is empty!");
            return "";
        }
        if (invoker.getModelsManager().getModels().getFirst().getOwnerId() != owner_id){
            invoker.getPrinter().print( "The target model belongs to another user.");
            return "";
        }
        long id = invoker.getModelsManager().getModels().getFirst().getId();
        if (invoker.getDatabaseHandler().removeModel(id)){
            invoker.getConnection().sendChangesToAll(new RemoveModelServerRequest(invoker.getModelsManager().findModelById(id, invoker.getPrinter())));
            invoker.getModelsManager().removeById(id, invoker.getPrinter());
            return "Remove command executed!";
        }
        invoker.getPrinter().print( "Model was not removed!");
        return "";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"remove_first\": This command removes the first model in the collection.");
    }
}
