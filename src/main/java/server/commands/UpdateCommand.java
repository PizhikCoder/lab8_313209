package server.commands;

import server.core.validators.ModelsValidator;
import server.visualizationhandlers.CirclesProcessor;
import shared.commands.enums.DataField;
import shared.connection.requests.AddModelServerRequest;
import shared.connection.requests.CommandRequest;
import shared.connection.requests.UpdateModelServerRequest;
import shared.connection.requests.ValidationRequest;
import server.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.core.exceptions.FileAccessException;
import shared.core.exceptions.FileDoesNotExistException;
import shared.core.exceptions.RecursionException;
import shared.core.models.MusicBand;
import shared.interfaces.IDataManipulationCommand;

import java.util.Map;

/**
 * The class contains an implementation of the update command
 */
public class UpdateCommand extends Command implements IDataManipulationCommand {
    private final int ID_INDEX = 1;
    private final int MAP_INDEX = 0;

    public UpdateCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(int owner_id, Object arguments) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException {
        long id = (long) ((Object[]) arguments)[ID_INDEX];
        Map<DataField, Object> data = (Map<DataField, Object>) ((Object[]) arguments)[MAP_INDEX];
        MusicBand model = invoker.getModelsManager().findModelById(id, invoker.getPrinter());
        if (model.getOwnerId() != owner_id) {
            invoker.getPrinter().print("The target model belongs to another user.");
            return "";
        }
        if (ModelsValidator.idExist(id) && invoker.getDatabaseHandler().updateModel(data, id)) {
            invoker.getModelsManager().updateModel(id, data, invoker.getPrinter());
            invoker.getConnection().sendChangesToAll(new UpdateModelServerRequest(model));
            CirclesProcessor circlesProcessor = CirclesProcessor.getInstance(invoker);
            circlesProcessor.process(model);
            return "Model updating executed!";
        }
        invoker.getPrinter().print("Models was not updated!");
        return "";
    }

    @Override
    public String getCommandInfo() {
        return "Command \"update <id>\": This command allows user to update existing objects in collection."
                + "\nArguments: Integer(>0)";
    }

    @Override
    public void validate(Object args) {
        long id = (long) args;
        if (invoker.getModelsManager().findModelById(id, invoker.getPrinter()) != null) {
            invoker.getConnection().send(new ValidationRequest(null, true));
        } else {
            invoker.getConnection().send(new ValidationRequest(null, false));
            invoker.getPrinter().print("Can not update.");
        }
    }
}
