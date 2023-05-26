package client.UI.resourcebundles.musicbandfieldsbundles;


import java.util.ListResourceBundle;

public class MusicBandFieldsRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = {
                {"id", "ID"},
                {"ownerId", "Omaniku ID"},
                {"creationDate", "Loomise kuupäev"},
                {"name", "Bändi nimi"},
                {"coordinateX", "Koordinaat X"},
                {"coordinateY", "Koordinaat Y"},
                {"numberOfParticipants", "Osalejate arv"},
                {"genre", "Muusika žanr"},
                {"personName", "Isiku nimi"},
                {"personHeight", "Isiku pikkus"},
                {"personNationality", "Isiku rahvus"},
                {"locationX", "Asukoha X-koordinaat"},
                {"locationY", "Asukoha Y-koordinaat"},
                {"locationZ", "Asukoha Z-koordinaat"}
        };

        return resources;
    }
}
