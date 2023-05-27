package client.UI.Controllers;

import client.MainApplication;
import client.UI.resourcebundles.enums.AuthorizationFormElements;
import client.UI.resourcebundles.enums.MainFormElements;
import client.UI.resourcebundles.enums.RuntimeOutputs;
import client.backend.commands.SignUpCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import client.backend.commands.Command;
import client.backend.commands.SignInCommand;
import client.backend.core.Invoker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.core.ClientInfo;
import shared.interfaces.IPrinter;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class AuthorizationFormController {
    private int SHOW_PASSWORD_TOOLTIP_DELAY = 1000;

    private int SCENE_HEIGHT = 600;

    private int SCENE_WIDTH = 800;

    private final int LANGUAGE_CHANGING_FORM_WIDTH = 300;


    private final int LANGUAGE_CHANGING_FORM_HEIGHT = 200;

    @FXML
    private TextField logInTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Menu settingsMenu;

    @FXML
    private MenuItem languageMenuItem;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;


    @FXML
    public void initialize() {
        MainFormController.getCurrentLocale().addListener(change -> updateLocale());
        updateLocale();
    }

    private void updateLocale(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("client.UI.resourcebundles.authorizationformbundles.AuthorizationFormRB", MainFormController.getCurrentLocale().get().getLocale());
        logInTextField.setPromptText(resourceBundle.getString(AuthorizationFormElements.LOG_IN_TEXT_FIELD.toString()));
        passwordTextField.setPromptText(resourceBundle.getString(AuthorizationFormElements.PASSWORD_TEXT_FIELD.toString()));
        settingsMenu.setText(resourceBundle.getString(AuthorizationFormElements.SETTINGS_MENU.toString()));
        languageMenuItem.setText(resourceBundle.getString(AuthorizationFormElements.LANGUAGE_MENU_ITEM.toString()));
        loginLabel.setText(resourceBundle.getString(AuthorizationFormElements.LOGIN_LABEL.toString()));
        passwordLabel.setText(resourceBundle.getString(AuthorizationFormElements.PASSWORD_LABEL.toString()));
        signUpButton.setText(resourceBundle.getString(AuthorizationFormElements.SIGN_UP_BUTTON.toString()));
        signInButton.setText(resourceBundle.getString(AuthorizationFormElements.SIGN_IN_BUTTON.toString()));
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
    protected void onSignInButtonPressed(ActionEvent event) throws ExecutionException, InterruptedException {
        Button button = (Button)event.getTarget();
        button.setDisable(true);
        Command command = new SignInCommand(Invoker.getInstance(), logInTextField.getText(), passwordTextField.getText());
        if (Invoker.getInstance().invokeCommand(command).get()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DataGridForm.fxml"));
                Parent parent = fxmlLoader.load();
                MainFormController mainFormController = fxmlLoader.getController();
                mainFormController.getUserMenu().setText(ClientInfo.getLogin() + "   ID:   " + ClientInfo.getUserId());
                Scene scene = new Scene(parent, SCENE_WIDTH, SCENE_HEIGHT);
                MainApplication.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        button.setDisable(false);
    }

    @FXML
    protected void onSignUpButtonPressed(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        Button button = (Button)actionEvent.getTarget();
        button.setDisable(true);
        Command command = new SignUpCommand(Invoker.getInstance(), logInTextField.getText(), passwordTextField.getText());
        if (Invoker.getInstance().invokeCommand(command).get()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DataGridForm.fxml"));
                Parent parent = fxmlLoader.load();
                MainFormController mainFormController = fxmlLoader.getController();
                mainFormController.getUserMenu().setText(ClientInfo.getLogin() + "   ID:   " + ClientInfo.getUserId());
                Scene scene = new Scene(parent, SCENE_WIDTH, SCENE_HEIGHT);
                MainApplication.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            IPrinter printer = Invoker.getInstance().getPrinter();
            printer.print("Client was not registered!");
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

    @FXML
    protected void onLanguageMenuItemPressed(ActionEvent actionEvent){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(LanguageChangingFormController.class.getResource("LanguageChangingForm.fxml"));
            Parent parent = fxmlLoader.load();
            LanguageChangingFormController languageChangingFormController = fxmlLoader.getController();
            Scene scene = new Scene(parent, LANGUAGE_CHANGING_FORM_WIDTH, LANGUAGE_CHANGING_FORM_HEIGHT);
            Stage stage = new Stage();
            languageChangingFormController.setCurrentStage(stage);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ioException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        }
    }
}
