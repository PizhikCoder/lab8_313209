package client.UI.resourcebundles.countrybundles;

import java.util.ListResourceBundle;

public class CountryRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russia", "Venemaa"},
                {"germany", "Saksamaa"},
                {"vatican", "Vatikan"},
                {"italy", "Itaalia"}
        };
        return contents;
    }
}
