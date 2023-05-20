package fxproject.UI.Controllers;

import fxproject.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import fxproject.backend.commands.Command;
import fxproject.backend.commands.SignInCommand;
import fxproject.backend.core.Invoker;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.core.ClientInfo;

import java.io.IOException;

public class AuthorizationFormController {
    private int SHOW_PASSWORD_TOOLTIP_DELAY = 1000;
    @FXML
    private TextField logInTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void initialize() {
    }

    @FXML
    protected void onPasswordFieldMouseEntered(MouseEvent mouseEvent){
        passwordTextField.setTooltip(new Tooltip(passwordTextField.getText()));
        passwordTextField.getTooltip().textProperty().bindBidirectional(passwordTextField.textProperty());
        passwordTextField.getTooltip().setShowDelay(new Duration(SHOW_PASSWORD_TOOLTIP_DELAY));
    }

    @FXML
    protected void onPasswordFieldMouseExited(MouseEvent mouseEvent){
        passwordTextField.setTooltip(null);
    }

    @FXML
    protected void onSignInButtonPressed(ActionEvent event) {
        Button button = (Button)event.getTarget();
        button.setDisable(true);
        Command command = new SignInCommand(Invoker.getInstance(), logInTextField.getText(), passwordTextField.getText());
        if (Invoker.getInstance().invokeCommand(command)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DataGridForm.fxml"));
                Parent parent = fxmlLoader.load();
                MainFormController mainFormController = fxmlLoader.getController();
                mainFormController.getUserMenu().setText(ClientInfo.getLogin());
                Scene scene = new Scene(parent, 800, 600);
                MainApplication.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        button.setDisable(false);
    }

    @FXML
    protected void onButtonMouseEntered(MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("""
                -fx-background-color: null;
                -fx-border-width: 2;
                -fx-border-radius: 50;
                -fx-border-color: brown
                """);
    }

    @FXML
    protected void onButtonMouseExited(MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("""
                -fx-background-color: null;
                -fx-border-width: 1;
                -fx-border-radius: 50;
                -fx-border-color: brown
                """);
    }
}
