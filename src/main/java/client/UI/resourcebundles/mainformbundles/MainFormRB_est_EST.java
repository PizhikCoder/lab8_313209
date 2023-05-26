package client.UI.resourcebundles.mainformbundles;

import java.util.ListResourceBundle;

public class MainFormRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"createFilterButton", "Loo filter"},
                {"removeFiltersButton", "Eemalda filtrid"},
                {"addButton", "Lisa"},
                {"addIfMinButton", "Lisa, kui min"},
                {"updateButton", "Uuenda"},
                {"removeButton", "Eemalda"},
                {"removeByIdButton", "Eemalda ID järgi"},
                {"clearButton", "Tühjenda"},
                {"filterLessThanFrontManButton", "Filtreeri väiksemad kui esiliige"},
                {"countGreaterThanFrontManButton", "Loenda suuremad kui esiliige"},
                {"groupCountingByCoordinatesButton", "Rühmita loendamine koordinaatide järgi"},
                {"controllersLabel", "Juhtelemendid"},
                {"infoMenu", "Info"},
                {"settingsMenu", "Seaded"},
                {"helpMenu", "Abi"},
                {"languageMenuItem", "Keel"},
                {"logOutMenuItem", "Logi välja"},
                {"executeScriptButton", "Käivita skript"}
        };
        return contents;
    }
}
