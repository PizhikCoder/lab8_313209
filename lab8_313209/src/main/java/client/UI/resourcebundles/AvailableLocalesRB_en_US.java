package client.UI.resourcebundles;

import java.util.ListResourceBundle;

public class AvailableLocalesRB_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russian", "Russian"},
                {"english", "English"}
        };
        return contents;
    }
}