package fxproject;

import fxproject.UI.Controllers.AuthorizationFormController;
import fxproject.backend.connection.ClientConnection;
import fxproject.backend.connection.ServerListenerThread;
import fxproject.backend.connection.interfaces.IClientConnection;
import fxproject.backend.printers.NotificationPrinter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import fxproject.backend.core.Invoker;
import shared.core.exceptions.ConnectionException;
import shared.interfaces.IPrinter;

import java.io.IOException;
import java.io.PipedOutputStream;

public class MainApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
//        Parent parent = FXMLLoader.load(getClass().getResource("DataGridForm.fxml"));
        Parent parent = FXMLLoader.load(AuthorizationFormController.class.getResource("AuthorizationForm.fxml"));
        primaryStage = stage;
        Scene scene = new Scene(parent, 660, 400);
        stage.setScene(scene);
        stage.setMinWidth(660);
        stage.setMinHeight(400);
        stage.setOpacity(0.95);
        stage.show();
    }

    public static void main(String[] args) {
        if (createConnection()) {
            launch();
        }
    }

    private static boolean createConnection(){
        try{
            IClientConnection connection = new ClientConnection("127.0.0.1", 2222);
            IPrinter printer = new NotificationPrinter();
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            Invoker invoker = Invoker.create(printer, pipedOutputStream, connection);
            connection.connect(invoker);
            ServerListenerThread serverListenerThread = new ServerListenerThread(pipedOutputStream,connection, printer);
            serverListenerThread.setDaemon(true);
            serverListenerThread.start();
            return true;
        }
        catch (ConnectionException connectionException){
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Connection error");
                alert.setContentText(connectionException.getMessage());
                alert.show();
            });
        }
        catch (IOException ioException){
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Can not run component");
                alert.setContentText("Can not initialize component...");
                alert.show();
            });
        }
        return false;
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
}