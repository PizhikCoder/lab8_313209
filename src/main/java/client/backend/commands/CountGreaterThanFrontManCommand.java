package client.backend.commands;

import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.core.Invoker;
import client.backend.core.MusicBandFieldsValidators;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import org.controlsfx.control.Notifications;
import shared.core.exceptions.CommandParamsException;
import shared.core.models.MusicBand;

import java.util.Arrays;

/**
 * The class contains an implementation of the count_greater_than_front_man command
 */
public class CountGreaterThanFrontManCommand extends Command{
    private MusicBand[] collection;

    private final int HEIGHT_INDEX = 0;

    private final int EXPECTED_ARGUMENTS_COUNT = 1;

    public CountGreaterThanFrontManCommand(MusicBand[] collection) {
        this.collection = collection;
    }

    @Override
    public boolean execute(String... args) throws CommandParamsException {
        if (collection.length == 0){
            Invoker.getInstance().getPrinter().print(CommandsAnswers.COLLECTION_IS_EMPTY.toString());
            return false;
        }
        if (args.length != EXPECTED_ARGUMENTS_COUNT){
            throw new CommandParamsException(args.length, EXPECTED_ARGUMENTS_COUNT);
        }
        if (!args[HEIGHT_INDEX].isBlank() && MusicBandFieldsValidators.personHeightCheck(args[HEIGHT_INDEX])) {
            startCounting(args);
            return true;
        }
        Invoker.getInstance().getPrinter().print(CommandsAnswers.COUNT_GREATER_THAN_FRONT_MAN_NOT_EXECUTED.toString());
        return false;
    }

    private void startCounting(String[] args){
        String value = args[HEIGHT_INDEX].replace(",", ".");
        float height = Float.parseFloat(value);
        long count = Arrays.stream(collection)
                .filter(x->x.getFrontMan().getHeight()!=null
                        && x.getFrontMan().getHeight()>height).count();
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(String.format(CommandsAnswers.COUNT_GREATER_THAN_FRONT_MAN_EXECUTED.toString(), height, count));
            alert.show();
        });
    }
}
