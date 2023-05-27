package client;

import client.UI.Controllers.AuthorizationFormController;
import client.UI.resourcebundles.enums.RuntimeOutputs;
import client.backend.connection.ClientConnection;
import client.backend.connection.ServerListenerThread;
import client.backend.connection.interfaces.IClientConnection;
import client.backend.printers.NotificationPrinter;
import client.backend.visualizationlogic.MusicBandPolygon;
import client.backend.visualizationlogic.computers.CirclesComputer;
import client.backend.visualizationlogic.computers.PolygonsFacade;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import client.backend.core.Invoker;
import shared.core.exceptions.ConnectionException;
import shared.interfaces.IPrinter;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.time.ZoneId;
import java.util.Arrays;

public class MainApplication extends Application {
    public final static int MAIN_SCENE_WIDTH = 800;
    public final static int MAIN_SCENE_HEIGHT = 600;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
//        Parent parent = FXMLLoader.load(getClass().getResource("DataGridForm.fxml"));
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
        if (createConnection()) {
            launch();
        }
    }

    private static boolean createConnection() {
        try {
            IClientConnection connection = new ClientConnection("127.0.0.1", 2222);
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
}