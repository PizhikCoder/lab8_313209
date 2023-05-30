package client;

import client.UI.Controllers.AuthorizationFormController;
import client.UI.resourcebundles.enums.RuntimeOutputs;
import client.backend.connection.ClientConnection;
import client.backend.connection.ServerListenerThread;
import client.backend.connection.interfaces.IClientConnection;
import client.backend.printers.NotificationPrinter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import client.backend.core.Invoker;
import shared.core.exceptions.ConnectionException;
import shared.interfaces.IPrinter;

import java.io.IOException;
import java.io.PipedOutputStream;

public class MainApplication extends Application {
    public final static int MAIN_SCENE_WIDTH = 800;
    public final static int MAIN_SCENE_HEIGHT = 600;
    private static final String HOST_NAME = "127.0.0.1";

    private static final int MAX_PORT_VALUE = 1023;

    private static final int PORT_INDEX = 0;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(AuthorizationFormController.class.getResource("AuthorizationForm.fxml"));
        primaryStage = stage;
        Scene scene = new Scene(parent, MAIN_SCENE_WIDTH, MAIN_SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setMinWidth(MAIN_SCENE_WIDTH);
        stage.setMinHeight(MAIN_SCENE_HEIGHT);
        stage.setOpacity(0.95);
        stage.show();
    }

    public static void main(String[] args) {
        int port = checkPort(args);
        if (port == -1) return;
        if (createConnection(port)) {
            launch();
        }
    }

    private static boolean createConnection(int port) {
        try {
            IClientConnection connection = new ClientConnection(HOST_NAME, port);
            IPrinter printer = new NotificationPrinter();
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            Invoker invoker = Invoker.create(printer, pipedOutputStream, connection);
            connection.connect(invoker);
            ServerListenerThread serverListenerThread = new ServerListenerThread(pipedOutputStream, connection, printer);
            serverListenerThread.setDaemon(true);
            serverListenerThread.start();
            return true;
        } catch (ConnectionException connectionException) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(RuntimeOutputs.CONNECTION_COULD_NOT_BE_ESTABLISHED.toString());
                alert.setContentText(connectionException.getMessage());
                alert.show();
            });
        } catch (IOException ioException) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
                alert.show();
            });
        }
        return false;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static int checkPort(String...args){
        int port;
        try{
            if (args.length == 0){
                System.err.println("Expected 1 argument, received 0");
                return -1;
            }
            port = Integer.parseInt(args[PORT_INDEX]);
            if(port<= MAX_PORT_VALUE){
                System.err.println("Can not start client on this port!");
                return -1;
            }
        }
        catch (NumberFormatException exception){
            System.err.println("Port in the wrong format. Expected Integer.");
            return -1;
        }
        return port;
    }
}