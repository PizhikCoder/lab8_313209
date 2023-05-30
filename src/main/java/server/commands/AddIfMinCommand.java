package server.commands;

import server.core.Invoker;
import server.core.validators.ModelsValidator;
import server.visualizationhandlers.CirclesProcessor;
import shared.commands.enums.DataField;
import shared.connection.requests.AddModelServerRequest;
import shared.connection.requests.CommandRequest;
import shared.connection.requests.ValidationRequest;
import shared.core.exceptions.*;
import shared.core.models.MusicBand;
import shared.interfaces.IDataManipulationCommand;

import java.util.Map;

/**
 * The class contains an implementation of the add_if_min command.
 */
public class AddIfMinCommand extends Command implements IDataManipulationCommand {
    private final int MAP_INDEX = 0;

    private final int ID_INDEX = 1;


    public AddIfMinCommand(Invoker invoker) {
        super(invoker);
    }

    @Override
    public String execute(int owner_id, Object  arguments) throws RecursionException, FileAccessException, CommandParamsException, FileDoesNotExistException, ArgumentLimitsException {
        if (ModelsValidator.idValueCheck((long)((Object[])arguments)[ID_INDEX])){
            Map<DataField,Object> data = (Map<DataField, Object>)((Object[])arguments)[MAP_INDEX];
            long model_id = (long)((Object[])arguments)[ID_INDEX];
            data.put(DataField.OWNER_ID, owner_id);
            MusicBand model = invoker.getModelsManager().createModel(data, model_id);
            int res = invoker.getDatabaseHandler().sendModel(model, model_id);
            if (res!=-1){
                invoker.getModelsManager().addModels(model);
                invoker.getConnection().sendChangesToAll(new AddModelServerRequest(model));
                CirclesProcessor circlesProcessor = CirclesProcessor.getInstance(invoker);
                circlesProcessor.process(model);
                invoker.getPrinter().print("Object was successfully created!");
            }
            return "Add If Min command executed!";
        }
        invoker.getPrinter().print("Model with this id already exist!");
        return "";
    }

    @Override
    public String getCommandInfo() {
        return String.format("Command \"add_if_min <id>\": This command creates a new collection item with the specified id if it is smaller than the smallest id in the collection."
        +"\nArguments: Integer (>0)");
    }

    @Override
    public void validate(Object args) {
        if (invoker.getModelsManager().getModels().isEmpty()){
            invoker.getPrinter().print("Collection is empty!");
        }
        else if (invoker.getModelsManager().getModels().getFirst().getId() > (long)args){
            invoker.getConnection().send(new ValidationRequest(null, true));
            return;
        }
        invoker.getPrinter().print("ID is not minimal!");
        invoker.getConnection().send(new ValidationRequest(null, false));
        invoker.getConnection().send(new CommandRequest(null, true));

    }
}
