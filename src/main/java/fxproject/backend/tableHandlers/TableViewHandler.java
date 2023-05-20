package fxproject.backend.tableHandlers;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import shared.core.models.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Predicate;

public class TableViewHandler {
    private TableView tableView;

    private ObservableList<MusicBand> modelsCollection;

    private FilteredList<MusicBand> filteredList;

    private SortedList<MusicBand> sortedList;

    private static ObservableList<Predicate> predicates;

    public TableViewHandler(TableView tableView, ObservableList modelsCollection) {
        this.tableView = tableView;
        this.modelsCollection = modelsCollection;
        predicates = FXCollections.observableArrayList();
        predicates.addListener(this::listChanged);
        filteredList = new FilteredList(modelsCollection);
        filteredList.setPredicate(this::checkPredicates);
        sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    public void initializeColumns() {
        TableColumnsInitializer tableColumnsInitializer = new TableColumnsInitializer(tableView);
        tableColumnsInitializer.initializeColumns();
    }

    private void listChanged(ListChangeListener.Change change) {
        filteredList.setPredicate(this::checkPredicates);
    }

    private boolean checkPredicates(Object o) {
        for (Predicate predicate : predicates) {
            if (!predicate.test(o)) {
                return false;
            }
        }
        return true;
    }

    public void addElement(MusicBand musicBand){
        Platform.runLater(()-> modelsCollection.add(musicBand));
    }

    public void updateElement(MusicBand newMusicBand){
        Platform.runLater(()->{
            if (modelsCollection.removeIf(oldMusicBand -> oldMusicBand.getId() == newMusicBand.getId())){
                modelsCollection.add(newMusicBand);
            }
        });
    }

    public void removeElement(MusicBand musicBand){
        Platform.runLater(()-> modelsCollection.removeIf(band->band.getId() == musicBand.getId()));
    }

    public static List<Predicate> getPredicates() {
        return predicates;
    }

}
