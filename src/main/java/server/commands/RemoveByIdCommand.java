package server.commands;


import server.core.Invoker;
import shared.connection.requests.AddModelServerRequest;
import shared.connection.requests.RemoveModelServerRequest;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import shared.core.models.MusicBand;
import shared.interfaces.IDataManipulationCommand;

/**
 * The class contains an implementation of the remove_by_id command
 */
public class RemoveByIdCommand extends Command  implements IDataManipulationCommand {

    public RemoveByIdCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(int owner_id, Object arguments) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {

        if (invoker.getModelsManager().getModels().isEmpty()){
            invoker.getPrinter().print("Collection is empty!");
            return "";
        }
        long id = (long)arguments;
        MusicBand model = invoker.getModelsManager().findModelById(id, invoker.getPrinter());
        if (model == null){
            invoker.getPrinter().print("Model does not exist!");
            return "";
        }
        if (model.getOwnerId() != owner_id){
            invoker.getPrinter().print("The target model belongs to another user.");
            return "";
        }
        if (invoker.getDatabaseHandler().removeModel(id)){
            invoker.getModelsManager().removeById(id, invoker.getPrinter());
            invoker.getConnection().sendChangesToAll(new RemoveModelServerRequest(model));
            return "Remove By Id command executed!";
        }
        invoker.getPrinter().print( "Model was not removed!");
        return "";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"remove_by_id <id>\": This command removes the model with the specified id from the collection." +
                "\nArguments: Integer(>0)");
    }
}
