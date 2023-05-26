package client.UI.resourcebundles.musicbandfieldsbundles;


import java.util.ListResourceBundle;

public class MusicBandFieldsRB_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = {
                {"id", "ID"},
                {"ownerId", "Owner ID"},
                {"creationDate", "Creation date"},
                {"name", "Band name"},
                {"coordinateX", "Coordinate X"},
                {"coordinateY", "Coordinate Y"},
                {"numberOfParticipants", "Number of participants"},
                {"genre", "Music genre"},
                {"personName", "Person name"},
                {"personHeight", "Person height"},
                {"personNationality", "Person nationality"},
                {"locationX", "Location X"},
                {"locationY", "Location Y"},
                {"locationZ", "Location Z"}
        };

        return resources;
    }
}
