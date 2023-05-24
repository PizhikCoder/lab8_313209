package client.backend.core;

import client.UI.Controllers.MainFormController;
import client.backend.commands.*;
import client.backend.commands.adapters.AddCommandAdapter;
import shared.core.models.MusicBand;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandsManager {
    private Map<String, Command> commandsCollection;

    public CommandsManager(){
        commandsCollection = new HashMap<>();
        initCommands();
    }

    private void initCommands(){
        commandsCollection.put("add", new AddCommandAdapter());
        commandsCollection.put("clear", new ClearCommand(Invoker.getInstance()));
        commandsCollection.put("count_greater_than_frontman", new CountGreaterThanFrontManCommand(MainFormController.getMainFormController().getTableViewHandler().getSortedList().toArray(MusicBand[]::new)));
        commandsCollection.put("execute_script", new ExecuteScriptCommand(Invoker.getInstance().getPrinter()));
        commandsCollection.put("filter_less_than_frontman", new FilterLessThanFrontManCommand(MainFormController.getMainFormController().getFiltersHBox()));
        commandsCollection.put("group_count_by_coordinates", new GroupCountingByCoordinatesCommand(MainFormController.getMainFormController().getTableViewHandler().getSortedList().toArray(MusicBand[]::new)));
        commandsCollection.put("remove_by_id", new RemoveByIdCommand(Invoker.getInstance()));
    }

    public Optional<Command> findCommand(String command){
        if (commandsCollection.containsKey(command)){
            return Optional.of(commandsCollection.get(command));
        }
        return Optional.empty();
    }

    public Map<String, Command> getCommandsCollection(){
        return commandsCollection;
    }
}
