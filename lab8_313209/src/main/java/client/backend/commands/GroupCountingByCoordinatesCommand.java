package client.backend.commands;

import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.core.Invoker;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import org.controlsfx.control.Notifications;
import shared.core.exceptions.*;
import shared.core.models.Coordinates;
import shared.core.models.MusicBand;

import java.util.Arrays;

public class GroupCountingByCoordinatesCommand extends Command{

    private final MusicBand[] collection;

    public GroupCountingByCoordinatesCommand(MusicBand[] collection){
        this.collection = collection;
    }

    @Override
    public boolean execute(String... args) {
        if (collection.length == 0){
            Invoker.getInstance().getPrinter().print(CommandsAnswers.COLLECTION_IS_EMPTY.toString());
            return false;
        }
        Coordinates[] uniqueCoordinates = Arrays.stream(collection).map(MusicBand::getCoordinates).distinct().toArray(Coordinates[]::new);
        String output = "";
        for(Coordinates coordinates : uniqueCoordinates){
            long count = Arrays.stream(collection).filter(x->x.getCoordinates().equals(coordinates)).count();
            output += String.format(CommandsAnswers.GROUP_COUNTING_BY_COORDINATES_EXECUTED.toString(),
                    coordinates, count);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        TextArea textArea = new TextArea(output);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);
        alert.show();
        return true;
    }
}
