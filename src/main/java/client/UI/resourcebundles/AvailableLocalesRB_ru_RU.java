package client.UI.resourcebundles;

import java.util.ListResourceBundle;

public class AvailableLocalesRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russian", "Русский"},
                {"english", "Английский"}
        };
        return contents;
    }
}