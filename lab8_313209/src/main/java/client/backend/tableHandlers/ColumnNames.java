package client.backend.tableHandlers;

import client.UI.Controllers.MainFormController;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ColumnNames {
    ID_COLUMN("id"),
    OWNER_ID_COLUMN("ownerId"),
    NAME_COLUMN("name"),
    COORDINATES_X_COLUMN("coordinateX"),
    COORDINATES_Y_COLUMN("coordinateY"),
    CREATION_DATE_COLUMN("creationDate"),
    NUMBER_OF_PARTICIPANTS_COLUMN("numberOfParticipants"),
    GENRE_COLUMN("genre"),
    PERSON_NAME_COLUMN("personName"),
    PERSON_HEIGHT_COLUMN("personHeight"),
    PERSON_NATIONALITY_COLUMN("personNationality"),
    LOCATION_X_COORDINATE("locationX"),
    LOCATION_Y_COORDINATE("locationY"),
    LOCATION_Z_COORDINATE("locationZ");
    private final String bundleObjectName;

    ColumnNames(String bundleObjectName){
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("client.UI.resourcebundles.MusicBandFieldsRB", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
