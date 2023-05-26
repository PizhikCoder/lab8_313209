package client.UI.resourcebundles.availablelocalesbundles;

import java.util.ListResourceBundle;

public class AvailableLocalesRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russian", "Ruso"},
                {"english", "Inglés"},
                {"estonian", "Estonio"},
                {"croatian", "Croata"},
                {"spanish", "Español"}
        };
        return contents;
    }
}
