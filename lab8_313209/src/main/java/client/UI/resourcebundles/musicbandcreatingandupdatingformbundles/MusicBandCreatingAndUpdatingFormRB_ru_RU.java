package client.UI.resourcebundles.musicbandcreatingandupdatingformbundles;

import java.util.ListResourceBundle;

public class MusicBandCreatingAndUpdatingFormRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"bandNameTextField", "Имя банды"},
                {"coordinateXTextField", "координата X"},
                {"coordinateYTextField", "координата Y"},
                {"numberOfParticipantsTextField", "число участников"},
                {"genreComboBox", "выберите жанр"},
                {"personNameTextField", "имя солиста"},
                {"personHeightTextField", "рост солиста"},
                {"personNationalityComboBox", "выберите национальность"},
                {"locationXTextField", "локация X"},
                {"locationYTextField", "локация Y"},
                {"locationZTextField", "локация Z"},

                {"bandNameLabel", "Имя банды"},
                {"coordinateXLabel", "Координата X"},
                {"coordinateYLabel", "Координата Y"},
                {"numberOfParticipantsLabel", "Количество участников"},
                {"genreLabel", "Выберите жанр"},
                {"personNameLabel", "Имя солиста"},
                {"personHeightLabel", "Рост солиста"},
                {"personNationalityLabel", "Национальность солиста"},
                {"locationXLabel", "Локация X"},
                {"locationYLabel", "Локация Y"},
                {"locationZLabel", "Локация Z"},

                {"cancelButton", "Отмена"},
                {"okButton", "Ок"}
        };
        return contents;
    }
}
