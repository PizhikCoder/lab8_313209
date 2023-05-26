package server;

import client.UI.resourcebundles.enums.RuntimeOutputs;
import server.connection.ConnectionHandler;
import server.database.DatabaseHandler;
import server.database.PostgresDataBase;
import server.database.interfaces.IDatabase;
import server.core.managers.ModelsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Main {
    public static Logger logger;

    private static int port;

    private static String bd_hostName;
    //"s368480"
    private static String pass;
    //"DrhmrowGMwNY6Euo"
    private static String url;
    //"jdbc:postgresql://localhost:5432/studs"

    private static final int PORT_INDEX = 0;

    private static final int HOSTNAME_INDEX = 1;

    private static final int PASS_INDEX = 2;

    private static final int URL_INDEX = 3;


    /**
     * Localhost address
     */
    private static final String HOST_NAME = "127.0.0.1";


    public static void main(String... args) {
        configureLogger();
        logger.log(Level.INFO, "Server is running.");

        checkArgs(args);

        ModelsManager modelsManager = new ModelsManager(new ArrayDeque<>());
        IDatabase database = new PostgresDataBase(bd_hostName, pass, url);
        if (!database.connect()) {
            return;
        }
        DatabaseHandler databaseHandler = new DatabaseHandler(database, modelsManager);
        ConnectionHandler connectionHandler = new ConnectionHandler(HOST_NAME, port, databaseHandler, databaseHandler, modelsManager, databaseHandler);
        connectionHandler.getInvoker().loadData();
        connectionHandler.waitConnection();
    }


    /**
     * Loads the logger configuration
     */
    private static void configureLogger() {
        try {
            new File("logs").mkdir();
        } catch (SecurityException exception) {
            System.err.println("Can not create logs directory.");
        }
        try (FileInputStream fileInputStream = new FileInputStream("log.config")) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
            logger = Logger.getLogger(Main.class.getName());
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
            System.out.println(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            System.exit(1);
        }
    }

    private static void checkArgs(String... args) {
        try {
            if (args.length != 4) {
                logger.log(Level.INFO, "Expected 4 argument, received 0");
                System.exit(1);
            }
            port = Integer.parseInt(args[PORT_INDEX]);
            bd_hostName = args[HOSTNAME_INDEX];
            pass = args[PASS_INDEX];
            url = args[URL_INDEX];
            if (port <= 1023) {
                logger.log(Level.INFO, "Can not start server on this port!");
                System.exit(1);
            }
        } catch (NumberFormatException exception) {
            logger.log(Level.WARNING, "Port in the wrong format. Expected Integer.");
            System.exit(1);
        }
    }
}
