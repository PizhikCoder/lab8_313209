package fxproject.UI.Controllers;

import fxproject.backend.tableHandlers.ColumnNames;
import fxproject.backend.tableHandlers.predicatefactory.AbstractPredicateFactory;
import fxproject.backend.tableHandlers.predicatefactory.FilterSigns;
import fxproject.backend.tableHandlers.predicatefactory.PredicateFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import shared.core.models.MusicBand;
import fxproject.backend.tableHandlers.FixedNameTableColumn;
import fxproject.backend.tableHandlers.TableViewHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Predicate;

public class FilterCreatorController {
    @FXML
    protected GridPane currentPane;
    @FXML
    private ComboBox columnsForFilteringComboBox;
    @FXML
    private ComboBox signsCombobox;
    @FXML
    private TextField filteringValueTextField;

    private DatePicker datePicker;

    private Stage currentStage;
    private HBox mainFiltersHBox;
    private TableView tableView;

    @FXML
    public void initialize() {
        mainFiltersHBox = MainFormController.getMainFormController().getFiltersHBox();
        tableView = MainFormController.getMainFormController().getTableView();
        initColumns();
        columnsForFilteringComboBox.valueProperty().addListener(change->columnsForFilteringComboBoxChanged());
        initSigns();
    }

    private void columnsForFilteringComboBoxChanged(){
        ColumnNames selectedColumn = ((FixedNameTableColumn)tableView.getColumns().stream().filter(x->((TableColumn)x).getText().equals(columnsForFilteringComboBox.getValue())).findAny().get()).getFixedName();
        if (selectedColumn == ColumnNames.CREATION_DATE_COLUMN){
            datePicker = new DatePicker();
            currentPane.getChildren().remove(filteringValueTextField);
            currentPane.add(datePicker, 1, 2);
            return;
        }
        if (datePicker != null){
            currentPane.getChildren().remove(datePicker);
            currentPane.add(filteringValueTextField, 1, 2);
        }
    }

    @FXML
    protected void onCancelButtonPressed(ActionEvent event) {
        currentStage.close();
    }

    @FXML
    protected void onCreateButtonPressed(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FilterForm.fxml"));
            Node node = fxmlLoader.load();
            FilterFormController filterFormController = fxmlLoader.getController();
            if (isColumnSelected(filterFormController)
                    && isSignSelected(filterFormController)
                    && initPredicate(filterFormController)
            ) {
                mainFiltersHBox.getChildren().add(node);
                currentStage.close();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Can not find filter fxml form!");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private boolean initPredicate(FilterFormController filterFormController) {

        FixedNameTableColumn fixedNameTableColumn = (FixedNameTableColumn) tableView.getColumns().stream()
                .filter(x -> ((TableColumn) x).getText() == columnsForFilteringComboBox.getValue().toString())
                .findAny().get();

        if (fixedNameTableColumn.getFixedName() == ColumnNames.CREATION_DATE_COLUMN && datePicker.getValue() != null){
            filteringValueTextField.setText(datePicker.getValue().toString());
        }

        AbstractPredicateFactory abstractPredicateFactory = new AbstractPredicateFactory();
        PredicateFactory predicateFactory = abstractPredicateFactory.createFactory(fixedNameTableColumn.getFixedName());
        Predicate<MusicBand> predicate =
                predicateFactory.createPredicate(FilterSigns.findByTextInterpretation(signsCombobox.getValue().toString()),
                        filteringValueTextField.getText());
        TableViewHandler.getPredicates().add(predicate);
        filterFormController.setPredicate(predicate);
        return true;
    }

    private void initColumns() {
        ObservableList<TableColumn> columns = tableView.getColumns();
        for (TableColumn tableColumn : columns) {
            columnsForFilteringComboBox.getItems().add(tableColumn.getText());
        }
    }

    private void initSigns() {
        for (FilterSigns filterSigns : FilterSigns.values()) {
            signsCombobox.getItems().add(filterSigns.getStringInterpretation());
        }
    }

    private boolean isColumnSelected(FilterFormController filterFormController) {
        if (columnsForFilteringComboBox.getValue() != null) {
            filterFormController.setColumnForFilteringLabel(columnsForFilteringComboBox.getValue().toString());
            return true;
        }
        Notifications.create().position(Pos.TOP_CENTER).text("Select column!").show();
        return false;
    }

    private boolean isSignSelected(FilterFormController filterFormController) {
        if (signsCombobox.getValue() != null) {
            filterFormController.setFilterSignLabel(signsCombobox.getValue().toString());
            return true;
        }
        Notifications.create().position(Pos.TOP_CENTER).text("Select sign!").show();
        return false;
    }

    private boolean isFilteringValueEntered(FilterFormController filterFormController) {
        if (!filteringValueTextField.getText().isBlank() ) {
            filterFormController.setFilteringValueLabel(filteringValueTextField.getText());
            return true;
        }
        Notifications.create().position(Pos.TOP_CENTER).text("Enter filtering value!").show();
        return false;
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

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }
}
