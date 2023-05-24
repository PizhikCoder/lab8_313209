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
 * The class contains an implementation of the clear command
 */
public class ClearCommand extends Command implements IDataManipulationCommand {
    public ClearCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(int owner_id, Object arguments) {
        MusicBand[] models = invoker.getModelsManager().getModels().toArray(MusicBand[]::new);
        for(MusicBand model : models){
            if (model.getOwnerId() != owner_id){
                continue;
            }
            if (invoker.getDatabaseHandler().removeModel(model.getId())){
                invoker.getModelsManager().removeById(model.getId(), invoker.getPrinter());
                invoker.getConnection().sendChangesToAll(new RemoveModelServerRequest(model));
            }
        }
        return "Models cleared!";
    }

    @Override
    public String getCommandInfo() {
        return "Command \"clear\": This command remove all models from collection.";
    }
}
