package client.UI.resourcebundles;

import java.util.ListResourceBundle;

public class MusicGenreRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"rock", "Рок"},
                {"blues", "Блюз"},
                {"pop", "Поп"},
                {"postRock", "Пост рок"}
        };
        return contents;
    }
}
