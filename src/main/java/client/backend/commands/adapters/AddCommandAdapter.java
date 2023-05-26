package client.backend.commands.adapters;

import client.UI.Controllers.MusicBandCreatingAndUpdatingFormController;
import client.backend.commands.AddCommand;
import client.backend.commands.Command;
import client.backend.core.Invoker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import shared.commands.enums.DataField;
import shared.core.exceptions.*;

import java.io.IOException;
import java.util.Map;

public class AddCommandAdapter extends Command {

    private final int MUSIC_BAND_CREATING_AND_UPDATING_FORM_HEIGHT = 600;

    private final int MUSIC_BAND_CREATING_AND_UPDATING_FORM_WIDTH = 400;

    @Override
    public boolean execute(String... args) throws CommandParamsException {
        try {
            MusicBandCreatingAndUpdatingFormController musicBandCreatingAndUpdatingFormController = initCreatingForm();
            Map<DataField, Object> data = musicBandCreatingAndUpdatingFormController.getData();
            if (data == null) return false;
            Command command = new AddCommand(data, Invoker.getInstance());
            return Invoker.getInstance().invokeCommand(command);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can not load music band creator form!");
            alert.show();
        }
        return false;
    }

    private MusicBandCreatingAndUpdatingFormController initCreatingForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicBandCreatingAndUpdatingFormController.class.getResource("MusicBandCreatingForm.fxml"));
        Parent node = fxmlLoader.load();
        Scene scene = new Scene(node, MUSIC_BAND_CREATING_AND_UPDATING_FORM_WIDTH, MUSIC_BAND_CREATING_AND_UPDATING_FORM_HEIGHT);
        Stage stage = new Stage();
        MusicBandCreatingAndUpdatingFormController musicBandCreatingAndUpdatingFormController = fxmlLoader.getController();
        musicBandCreatingAndUpdatingFormController.setCurrentStage(stage);
        stage.setScene(scene);
        stage.showAndWait();
        return musicBandCreatingAndUpdatingFormController;
    }

}
