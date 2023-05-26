package client.UI.resourcebundles.availablelocalesbundles;

import java.util.ListResourceBundle;

public class AvailableLocalesRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russian", "Русский"},
                {"english", "Английский"},
                {"estonian", "Эстонский"},
                {"croatian", "Хорватский"},
                {"spanish", "Испанский"}
        };
        return contents;
    }
}
