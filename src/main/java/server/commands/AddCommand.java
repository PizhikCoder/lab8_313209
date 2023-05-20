package server.commands;

import server.core.Invoker;
import shared.commands.enums.DataField;
import shared.connection.requests.AddModelServerRequest;
import shared.core.models.MusicBand;
import shared.interfaces.IDataManipulationCommand;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

/**
 * The class contains an implementation of the add command
 */
public class AddCommand extends Command implements IDataManipulationCommand {

    public AddCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public String execute(int ownerId, Object arguments) {
        Map<DataField,Object> data = (Map<DataField, Object>) arguments;
        data.put(DataField.OWNER_ID, ownerId);
        invoker.getPrinter().print("Starting object creating...");
        MusicBand band = invoker.getModelsManager().createModel(data);
        int modelId = invoker.getDatabaseHandler().sendModel(band);
        if(modelId!=-1){
            band.setId(modelId);
            invoker.getModelsManager().addModels(band);
            invoker.getConnection().sendChangesToAll(new AddModelServerRequest(band));
            return "Object was successfully created!";
        }
        invoker.getPrinter().print("Object wasn't created.");
        return "";
    }

    @Override
    public String getCommandInfo(){
        return String.format("Command \"add\": This command allows you to create a new music group model. When you enter the command, you will be prompted for fields to enter.");
    }
}
