package client.UI.resourcebundles.countrybundles;

import java.util.ListResourceBundle;

public class CountryRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russia", "Rusija"},
                {"germany", "Njemačka"},
                {"vatican", "Vatikan"},
                {"italy", "Italija"}
        };
        return contents;
    }
}
