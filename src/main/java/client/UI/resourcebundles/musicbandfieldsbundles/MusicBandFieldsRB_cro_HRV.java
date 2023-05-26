package client.UI.resourcebundles.musicbandfieldsbundles;


import java.util.ListResourceBundle;

public class MusicBandFieldsRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = {
                {"id", "ID"},
                {"ownerId", "ID vlasnika"},
                {"creationDate", "Datum stvaranja"},
                {"name", "Naziv benda"},
                {"coordinateX", "Koordinata X"},
                {"coordinateY", "Koordinata Y"},
                {"numberOfParticipants", "Broj sudionika"},
                {"genre", "Žanr glazbe"},
                {"personName", "Ime osobe"},
                {"personHeight", "Visina osobe"},
                {"personNationality", "Nacionalnost osobe"},
                {"locationX", "Lokacija X"},
                {"locationY", "Lokacija Y"},
                {"locationZ", "Lokacija Z"}
        };

        return resources;
    }
}
