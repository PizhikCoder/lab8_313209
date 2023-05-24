package client.UI.resourcebundles;


import java.util.ListResourceBundle;

public class MusicBandFieldsRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = {
                {"id", "ID"},
                {"ownerId", "ID владельца"},
                {"creationDate", "Дата создания"},
                {"name", "Имя группы"},
                {"coordinateX", "Координата X"},
                {"coordinateY", "Координата Y"},
                {"numberOfParticipants", "Число участников"},
                {"genre", "Жанр музыки"},
                {"personName", "Имя солиста"},
                {"personHeight", "Рост солиста"},
                {"personNationality", "Национальность солиста"},
                {"locationX", "Локация X"},
                {"locationY", "Локация Y"},
                {"locationZ", "Локация Z"}
        };

        return resources;
    }
}
