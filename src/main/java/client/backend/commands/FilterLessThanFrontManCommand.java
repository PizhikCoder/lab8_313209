package client.backend.commands;

import client.UI.Controllers.FilterCreatorFormController;
import client.UI.Controllers.FilterFormController;
import client.backend.core.MusicBandFieldsValidators;
import client.backend.tableHandlers.ColumnNames;
import client.backend.tableHandlers.TableViewHandler;
import client.backend.tableHandlers.predicatefactory.AbstractPredicateFactory;
import client.backend.tableHandlers.predicatefactory.FilterSigns;
import client.backend.tableHandlers.predicatefactory.PredicateFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import shared.core.exceptions.CommandParamsException;
import shared.core.models.MusicBand;

import java.io.IOException;
import java.util.function.Predicate;

public class FilterLessThanFrontManCommand extends Command {
    private final HBox filtersHBox;

    private final int HEIGHT_INDEX = 0;

    private final int EXPECTED_ARGUMENTS_COUNT = 1;

    public FilterLessThanFrontManCommand(HBox filtersHBox) {
        this.filtersHBox = filtersHBox;
    }

    @Override
    public boolean execute(String... args) throws CommandParamsException {
        try {
            if (args.length != EXPECTED_ARGUMENTS_COUNT) {
                throw new CommandParamsException(args.length, EXPECTED_ARGUMENTS_COUNT);
            }
            if (MusicBandFieldsValidators.personHeightCheck(args[HEIGHT_INDEX])) {
                String value = args[HEIGHT_INDEX].replace(",", ".");
                float height = Float.parseFloat(value);
                FXMLLoader fxmlLoader = new FXMLLoader(FilterCreatorFormController.class.getResource("FilterForm.fxml"));
                Node node = fxmlLoader.load();
                FilterFormController filterFormController = fxmlLoader.getController();
                filterFormController.setFilteringValueLabel(ColumnNames.PERSON_HEIGHT_COLUMN.toString());
                filterFormController.setFilterSignLabel(FilterSigns.LESS_THAN);
                filterFormController.setFilteringValueLabel(String.valueOf(height));
                filtersHBox.getChildren().add(node);
                initPredicate(filterFormController, String.valueOf(height));
                return true;
            }
        } catch (IOException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Can not find filter fxml form!");
                alert.setContentText(e.getMessage());
            });
        }
        return false;
    }

    private boolean initPredicate(FilterFormController filterFormController, String filteringValue) {

        AbstractPredicateFactory abstractPredicateFactory = new AbstractPredicateFactory();
        PredicateFactory predicateFactory = abstractPredicateFactory.createFactory(ColumnNames.PERSON_HEIGHT_COLUMN);
        Predicate<MusicBand> predicate =
                predicateFactory.createPredicate(FilterSigns.LESS_THAN, filteringValue);
        TableViewHandler.getPredicates().add(predicate);
        filterFormController.setPredicate(predicate);
        return true;
    }
}
