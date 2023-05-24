package client.UI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.util.concurrent.SynchronousQueue;

public class AdditionalFormOfDataCollectionController {
    @FXML
    private TextField valueTextField;

    private Stage currentStage;

    @FXML
    public void initialize(){
    }

    @FXML
    protected void onOkButtonPressed(ActionEvent actionEvent){
        if (valueTextField.getText().isBlank()){
            Notifications.create().position(Pos.TOP_CENTER).text("Enter value!").show();
        }
        currentStage.close();
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
    protected void onCancelButtonPressed(ActionEvent event) {
        valueTextField.setText("");
        currentStage.close();
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

    public void setCurrentStage(Stage currentStage){
        this.currentStage = currentStage;
    }

    public void setPromptText(String promptText){
        valueTextField.setPromptText(promptText);
    }

    public String getResult(){
        return valueTextField.getText();
    }
}
