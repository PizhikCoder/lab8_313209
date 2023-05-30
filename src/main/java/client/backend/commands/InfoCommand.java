package client.backend.commands;

import client.UI.resourcebundles.enums.CommandsAnswers;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import shared.core.exceptions.CommandParamsException;
import shared.core.models.MusicBand;

import java.util.List;

public class InfoCommand extends Command{
    private final List<MusicBand> collection;

    public InfoCommand(List<MusicBand> collection){
        this.collection = collection;
    }

    @Override
    public boolean execute(String... args) throws CommandParamsException {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(String.format(CommandsAnswers.INFO_EXECUTED.toString(), collection.size()));
            alert.show();
        });
        return true;
    }
}
