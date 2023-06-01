package client.backend.commands.adapters;

import client.UI.Controllers.MusicBandCreatingAndUpdatingFormController;
import client.UI.resourcebundles.enums.RuntimeOutputs;
import client.backend.commands.AddCommand;
import client.backend.commands.Command;
import client.backend.commands.UpdateCommand;
import client.backend.core.Invoker;
import client.backend.core.MusicBandFieldsValidators;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import shared.commands.enums.DataField;
import shared.core.exceptions.CommandParamsException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class UpdateCommandAdapter extends Command {

    private final int EXPECTED_ARGUMENTS_COUNT = 1;

    private final int ID_INDEX = 0;

    private final int MUSIC_BAND_CREATING_AND_UPDATING_FORM_HEIGHT = 600;

    private final int MUSIC_BAND_CREATING_AND_UPDATING_FORM_WIDTH = 400;

    private volatile CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public boolean execute(String... args) throws CommandParamsException {
        if (args.length != EXPECTED_ARGUMENTS_COUNT){
            throw new CommandParamsException(args.length, EXPECTED_ARGUMENTS_COUNT);
        }
        try {
            if (MusicBandFieldsValidators.bandIdValidate(args[ID_INDEX])){
                long id = Long.parseLong(args[ID_INDEX]);
                MusicBandCreatingAndUpdatingFormController musicBandCreatingAndUpdatingFormController = initCreatingForm();
                Map<DataField, Object> data = musicBandCreatingAndUpdatingFormController.getData();
                if (data == null) return false;
                Command command = new UpdateCommand(Invoker.getInstance(), id, data);
                return Invoker.getInstance().invokeCommand(command).get();
            }
        } catch (IOException exception) {
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
                alert.show();
            });
        } catch (ExecutionException | InterruptedException e) {}
        return false;
    }

    private MusicBandCreatingAndUpdatingFormController initCreatingForm() throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicBandCreatingAndUpdatingFormController.class.getResource("MusicBandCreatingForm.fxml"));
        Parent node = fxmlLoader.load();
        Scene scene = new Scene(node, MUSIC_BAND_CREATING_AND_UPDATING_FORM_WIDTH, MUSIC_BAND_CREATING_AND_UPDATING_FORM_HEIGHT);
        MusicBandCreatingAndUpdatingFormController musicBandCreatingAndUpdatingFormController = fxmlLoader.getController();
        Platform.runLater(()->{
            Stage stage = new Stage();
            musicBandCreatingAndUpdatingFormController.setCurrentStage(stage);
            stage.setScene(scene);
            stage.showAndWait();
            countDownLatch.countDown();
            countDownLatch = new CountDownLatch(1);
        });
        countDownLatch.await();
        return musicBandCreatingAndUpdatingFormController;
    }
}
