package client.UI.resourcebundles.countrybundles;

import java.util.ListResourceBundle;

public class CountryRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russia", "Rusia"},
                {"germany", "Alemania"},
                {"vatican", "Vaticano"},
                {"italy", "Italia"}
        };
        return contents;
    }
}
