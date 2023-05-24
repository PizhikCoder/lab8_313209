package client.UI.resourcebundles.musicbandcreatingandupdatingformbundles;

import java.util.ListResourceBundle;

public class MusicBandCreatingAndUpdatingFormRB_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"bandNameTextField", "band name"},
                {"coordinateXTextField", "coordinate X"},
                {"coordinateYTextField", "coordinate Y"},
                {"numberOfParticipantsTextField", "number of participants"},
                {"genreComboBox", "select genre"},
                {"personNameTextField", "person name"},
                {"personHeightTextField", "person height"},
                {"personNationalityComboBox", "select nationality"},
                {"locationXTextField", "location X"},
                {"locationYTextField", "location Y"},
                {"locationZTextField", "location Z"},

                {"bandNameLabel", "Band name"},
                {"coordinateXLabel", "Coordinate X"},
                {"coordinateYLabel", "Coordinate Y"},
                {"numberOfParticipantsLabel", "Number of participants"},
                {"genreLabel", "Select genre"},
                {"personNameLabel", "Person name"},
                {"personHeightLabel", "Person height"},
                {"personNationalityLabel", "Person nationality"},
                {"locationXLabel", "Location X"},
                {"locationYLabel", "Location Y"},
                {"locationZLabel", "Location Z"},

                {"cancelButton", "Cancel"},
                {"okButton", "Ok"}
        };
        return contents;
    }
}
