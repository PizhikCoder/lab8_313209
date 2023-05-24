package client.backend.tableHandlers;

import client.UI.Controllers.MainFormController;
import javafx.beans.property.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.core.models.Country;
import shared.core.models.MusicBand;

import java.time.ZonedDateTime;

public class TableColumnsInitializer {
    private TableView tableView;

    public TableColumnsInitializer(TableView tableView){
        this.tableView = tableView;
    }

    public void initializeColumns() {
        TableColumn<MusicBand, Long> idColumn = new FixedNameTableColumn<>(ColumnNames.ID_COLUMN);
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn<MusicBand, String> nameColumn = new FixedNameTableColumn<>(ColumnNames.NAME_COLUMN);
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<MusicBand, Integer> ownerIdColumn = new FixedNameTableColumn<>(ColumnNames.OWNER_ID_COLUMN);
        ownerIdColumn.setCellValueFactory(new PropertyValueFactory("ownerId"));
        TableColumn<MusicBand, Integer> coordinateXColumn = new FixedNameTableColumn<>(ColumnNames.COORDINATES_X_COLUMN);
        coordinateXColumn.setCellValueFactory(cellValue -> new SimpleIntegerProperty(cellValue.getValue().getCoordinates().getX()).asObject());
        TableColumn<MusicBand, Double> coordinateYColumn = new FixedNameTableColumn<>(ColumnNames.COORDINATES_Y_COLUMN);
        coordinateYColumn.setCellValueFactory(cellValue -> new SimpleDoubleProperty(cellValue.getValue().getCoordinates().getY()).asObject());
        TableColumn<MusicBand, ZonedDateTime> creationDateColumn = new FixedNameTableColumn<>(ColumnNames.CREATION_DATE_COLUMN);
        creationDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getCreationDate().withZoneSameInstant(MainFormController.getCurrentLocale().get().getZoneID())));
        TableColumn<MusicBand, Integer> numberOfParticipantsColumn = new FixedNameTableColumn<>( ColumnNames.NUMBER_OF_PARTICIPANTS_COLUMN);
        numberOfParticipantsColumn.setCellValueFactory(new PropertyValueFactory("numberOfParticipants"));
        TableColumn<MusicBand, String> genre = new FixedNameTableColumn<>(ColumnNames.GENRE_COLUMN);
        genre.setCellValueFactory(new PropertyValueFactory("genre"));
        TableColumn<MusicBand, String> personNameColumn = new FixedNameTableColumn<>(ColumnNames.PERSON_NAME_COLUMN);
        personNameColumn.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getFrontMan().getName()));
        TableColumn<MusicBand, Float> personHeightColumn = new FixedNameTableColumn<>(ColumnNames.PERSON_HEIGHT_COLUMN);
        personHeightColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getFrontMan().getHeight()));
        TableColumn<MusicBand, Country> personNationalityColumn = new FixedNameTableColumn<>(ColumnNames.PERSON_NATIONALITY_COLUMN);
        personNationalityColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty(cellValue.getValue().getFrontMan().getNationality()));
        TableColumn<MusicBand, Integer> locationXColumn = new FixedNameTableColumn<>(ColumnNames.LOCATION_X_COORDINATE);
        locationXColumn.setCellValueFactory(cellValue -> new SimpleIntegerProperty(cellValue.getValue().getFrontMan().getLocation().getX()).asObject());
        TableColumn<MusicBand, Float> locationYColumn = new FixedNameTableColumn<>(ColumnNames.LOCATION_Y_COORDINATE);
        locationYColumn.setCellValueFactory(cellValue -> new SimpleFloatProperty(cellValue.getValue().getFrontMan().getLocation().getY()).asObject());
        TableColumn<MusicBand, Float> locationZColumn = new FixedNameTableColumn<>(ColumnNames.LOCATION_Z_COORDINATE);
        locationZColumn.setCellValueFactory(cellValue -> new SimpleFloatProperty(cellValue.getValue().getFrontMan().getLocation().getZ()).asObject());
        tableView.getColumns().setAll(idColumn, nameColumn, ownerIdColumn, coordinateXColumn, coordinateYColumn, creationDateColumn,
                numberOfParticipantsColumn, genre, personNameColumn, personHeightColumn, personNationalityColumn, locationXColumn, locationYColumn, locationZColumn);
    }

}
