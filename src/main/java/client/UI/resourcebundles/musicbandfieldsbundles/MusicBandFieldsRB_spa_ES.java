package client.UI.resourcebundles.musicbandfieldsbundles;


import java.util.ListResourceBundle;

public class MusicBandFieldsRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = {
                {"id", "ID"},
                {"ownerId", "ID del propietario"},
                {"creationDate", "Fecha de creación"},
                {"name", "Nombre de la banda"},
                {"coordinateX", "Coordenada X"},
                {"coordinateY", "Coordenada Y"},
                {"numberOfParticipants", "Número de participantes"},
                {"genre", "Género musical"},
                {"personName", "Nombre de la persona"},
                {"personHeight", "Altura de la persona"},
                {"personNationality", "Nacionalidad de la persona"},
                {"locationX", "Ubicación X"},
                {"locationY", "Ubicación Y"},
                {"locationZ", "Ubicación Z"}
        };
        return resources;
    }
}
