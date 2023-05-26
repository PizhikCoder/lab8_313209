package client.UI.resourcebundles.musicgenrebundles;

import java.util.ListResourceBundle;

public class MusicGenreRB_cro_HRV extends ListResourceBundle {
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
