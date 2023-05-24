package server.core;

import server.commands.Command;
import server.connection.interfaces.IServerConnection;
import server.core.managers.CommandsManager;
import server.core.managers.ModelsManager;
import server.database.interfaces.IDatabaseHandlerDAO;
import shared.connection.requests.CommandRequest;
import shared.core.exceptions.*;
import shared.core.models.MusicBand;
import server.core.validators.ModelsValidator;
import shared.interfaces.IDataLoader;
import shared.interfaces.IDataManipulationCommand;
import shared.interfaces.IDataSaver;
import shared.interfaces.IPrinter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class. Contains all the logic for linking all classes of the programme.
 */
public class Invoker {

    private static final Logger logger = Logger.getLogger(Invoker.class.getName());

    private static boolean isDataLoading = false;

    private IPrinter printer;

    private ModelsManager modelsManager;

    private IServerConnection connection;

    private IDataSaver dataSaver;

    private IDataLoader dataLoader;

    private CommandsManager commandsManager;

    private IDatabaseHandlerDAO databaseHandler;


    public Invoker(IPrinter printer, IDataSaver saver, IDataLoader loader, ModelsManager modelsManager, CommandsManager commandsManager, IDatabaseHandlerDAO databaseHandler) {
        this.printer = printer;
        this.modelsManager = modelsManager;
        this.dataSaver = saver;
        this.dataLoader = loader;
        this.commandsManager = commandsManager;
        this.databaseHandler = databaseHandler;
    }

    /**
     * Invoke command logic.
     *
     * @param command   command's object.
     * @param arguments command's arguments.
     */
    public void invokeCommand(Command command, Object arguments, int user_id) {
        try {
            if (command != null) {
                String result = command.execute(user_id, arguments);
                if (!result.isBlank()) {
                    printer.print(result);
                    connection.send(new CommandRequest(null, true));
                }
                else {
                    connection.send(new CommandRequest(null, false));
                }
            }
        } catch (RecursionException | FileAccessException | CommandParamsException | FileDoesNotExistException |
                 ArgumentLimitsException ex) {
            logger.log(Level.WARNING, "Something went wrong while working with command.", ex);
        }
    }

    /**
     * This method loads data from file
     */
    public void loadData() {
        logger.log(Level.INFO, "Data loading started...");
        isDataLoading = true;
        ArrayDeque<MusicBand> queue = new ArrayDeque<>();

        MusicBand[] arr = ModelsValidator.modelsCheck(dataLoader.load(), this);
        queue.addAll(Arrays.asList(arr));

        isDataLoading = false;
        modelsManager.getUsedIDs().clear();
        modelsManager.addModels(queue);
        modelsManager.sort();
        logger.log(Level.INFO, "Data loading finished.");
    }

    public void setConnection(IServerConnection connection) {
        this.connection = connection;
    }

    public static boolean getIsDataLoading() {
        return isDataLoading;
    }

    public IPrinter getPrinter() {
        return printer;
    }


    public ModelsManager getModelsManager() {
        return modelsManager;
    }

    public IDataSaver getDataSaver() {
        return dataSaver;
    }


    public IServerConnection getConnection() {
        return connection;
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }

    public IDatabaseHandlerDAO getDatabaseHandler() {
        return databaseHandler;
    }

}
