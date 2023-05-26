package client.UI.resourcebundles.musicbandcreatingandupdatingformbundles;

import java.util.ListResourceBundle;

public class MusicBandCreatingAndUpdatingFormRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"bandNameTextField", "naziv benda"},
                {"coordinateXTextField", "koordinata X"},
                {"coordinateYTextField", "koordinata Y"},
                {"numberOfParticipantsTextField", "broj sudionika"},
                {"genreComboBox", "odaberite žanr"},
                {"personNameTextField", "ime osobe"},
                {"personHeightTextField", "visina osobe"},
                {"personNationalityComboBox", "odaberite nacionalnost"},
                {"locationXTextField", "lokacija X"},
                {"locationYTextField", "lokacija Y"},
                {"locationZTextField", "lokacija Z"},
                {"bandNameLabel", "Naziv benda"},
                {"coordinateXLabel", "Koordinata X"},
                {"coordinateYLabel", "Koordinata Y"},
                {"numberOfParticipantsLabel", "Broj sudionika"},
                {"genreLabel", "Odaberite žanr"},
                {"personNameLabel", "Ime osobe"},
                {"personHeightLabel", "Visina osobe"},
                {"personNationalityLabel", "Nacionalnost osobe"},
                {"locationXLabel", "Lokacija X"},
                {"locationYLabel", "Lokacija Y"},
                {"locationZLabel", "Lokacija Z"},

                {"cancelButton", "Odustani"},
                {"okButton", "Ok"}
        };
        return contents;
    }
}
