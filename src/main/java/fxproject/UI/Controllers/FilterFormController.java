package fxproject.UI.Controllers;

import fxproject.backend.tableHandlers.TableViewHandler;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.Notifications;

import java.util.function.Predicate;

public class FilterFormController {
    @FXML
    private GridPane filterFormMainPane;
    @FXML
    private Label columnForFilteringLabel;
    @FXML
    private Label filterSignLabel;
    @FXML
    private Label filteringValueLabel;

    private Predicate predicate;

    private EventHandler removeButtonPressedEventHandler;

    @FXML
    public void initialize(){
        removeButtonPressedEventHandler = this::removeListeners;
        MainFormController.getMainFormController().getRemoveFiltersButton().addEventHandler(MouseEvent.MOUSE_CLICKED, removeButtonPressedEventHandler);

    }

    private void tableChangesListener(ListChangeListener.Change change){
        Notifications.create().position(Pos.TOP_CENTER).text("Table changed").show();
    }
    @FXML
    private void onCloseButtonPressed(ActionEvent actionEvent){
        MainFormController mainFormController = MainFormController.getMainFormController();
        mainFormController.getFiltersHBox().getChildren().remove(filterFormMainPane);
        removeListeners(null);
    }

    private void removeListeners(Event event){
        MainFormController.getMainFormController().getRemoveFiltersButton().removeEventHandler(MouseEvent.MOUSE_CLICKED, removeButtonPressedEventHandler);
        TableViewHandler.getPredicates().remove(predicate);
    }

    public void setColumnForFilteringLabel(String value){
        columnForFilteringLabel.setText(value);
    }

    public void setFilterSignLabel(String value){
        filterSignLabel.setText(value);
    }

    public void setFilteringValueLabel(String value){
        filteringValueLabel.setText(value);
    }

    public void setPredicate(Predicate predicate){
        this.predicate = predicate;
    }

}
