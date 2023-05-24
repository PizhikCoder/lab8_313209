package client.UI.resourcebundles;

import java.util.ListResourceBundle;

public class MusicGenreRB_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"rock", "Rock"},
                {"blues", "Blues"},
                {"pop", "Pop"},
                {"postRock", "Post Rock"}
        };
        return contents;
    }
}
