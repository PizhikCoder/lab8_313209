package client.UI.resourcebundles.enums;

public enum MusicBandCreatingAndUpdatingFormElements {
    
    BAND_NAME_TEXT_FIELD("bandNameTextField"),
    COORDINATE_X_TEXT_FIELD("coordinateXTextField"),
    COORDINATE_Y_TEXT_FIELD("coordinateYTextField"),
    NUMBER_OF_PARTICIPANTS_TEXT_FIELD("numberOfParticipantsTextField"),
    GENRE_COMBO_BOX("genreLabel"),
    PERSON_NAME_TEXT_FIELD("personNameTextField"),
    PERSON_HEIGHT_TEXT_FIELD("personHeightTextField"),
    PERSON_NATIONALITY_COMBO_BOX("personNationalityLabel"),
    LOCATION_X_TEXT_FIELD("locationXTextField"),
    LOCATION_Y_TEXT_FIELD("locationYTextField"),
    LOCATION_Z_TEXT_FIELD("locationZTextField"),
    BAND_NAME_LABEL("bandNameLabel"),
    COORDINATE_X_LABEL("coordinateXLabel"),
    COORDINATE_Y_LABEL("coordinateYLabel"),
    NUMBER_OF_PARTICIPANTS_LABEL("numberOfParticipantsLabel"),
    GENRE_LABEL("genreLabel"),
    PERSON_NAME_LABEL("personNameLabel"),
    PERSON_HEIGHT_LABEL("personHeightLabel"),
    PERSON_NATIONALITY_LABEL("personNationalityLabel"),
    LOCATION_X_LABEL("locationXLabel"),
    LOCATION_Y_LABEL("locationYLabel"),
    LOCATION_Z_LABEL("locationZLabel"),
    OK_Button("okButton"),
    CANCEL_BUTTON("cancelButton");
    private final String bundleObjectName;

    MusicBandCreatingAndUpdatingFormElements (String bundleObjectName){
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        return bundleObjectName;
    }
}
