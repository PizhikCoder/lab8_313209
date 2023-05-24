package client.UI.resourcebundles;

import java.util.ListResourceBundle;

public class CountryRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"russia", "Россия"},
                {"germany", "Германия"},
                {"vatican", "Ватикан"},
                {"italy", "Италия"}
        };
        return contents;
    }
}
