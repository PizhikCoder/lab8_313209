package client.UI.resourcebundles.mainformbundles;

import java.util.ListResourceBundle;

public class MainFormRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"createFilterButton", "Stvori filter"},
                {"removeFiltersButton", "Ukloni filtere"},
                {"addButton", "Dodaj"},
                {"addIfMinButton", "Dodaj ako je najmanji"},
                {"updateButton", "Ažuriraj"},
                {"removeButton", "Ukloni"},
                {"removeByIdButton", "Ukloni prema ID-u"},
                {"clearButton", "Očisti"},
                {"filterLessThanFrontManButton", "Filtriraj manje od vođe benda"},
                {"countGreaterThanFrontManButton", "Broj veći od vođe benda"},
                {"groupCountingByCoordinatesButton", "Grupno brojanje prema koordinatama"},
                {"controllersLabel", "Kontrole"},
                {"infoMenu", "Informacije"},
                {"settingsMenu", "Postavke"},
                {"helpMenu", "Pomoć"},
                {"languageMenuItem", "Jezik"},
                {"logOutMenuItem", "Odjava"},
                {"executeScriptButton", "Izvrši skriptu"},
                {"visualizeButton", "Vizualiziraj"}
        };
        return contents;
    }
}
