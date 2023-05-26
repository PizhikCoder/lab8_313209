package client.UI.resourcebundles.availablelocalesbundles;

import java.util.ListResourceBundle;

public class AvailableLocalesRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russian", "Vene"},
                {"english", "Inglise"},
                {"estonian", "Eesti"},
                {"croatian", "Horvaadi"},
                {"spanish", "Hispaania"}
        };
        return contents;
    }
}
