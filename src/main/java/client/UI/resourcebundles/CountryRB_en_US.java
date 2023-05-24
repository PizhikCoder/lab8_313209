package client.UI.resourcebundles;

import java.util.ListResourceBundle;

public class CountryRB_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russia", "Russia"},
                {"germany", "Germany"},
                {"vatican", "Vatican"},
                {"italy", "Italy"}
        };
        return contents;
    }
}
