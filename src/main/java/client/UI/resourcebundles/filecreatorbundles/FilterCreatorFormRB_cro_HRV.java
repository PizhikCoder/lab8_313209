package client.UI.resourcebundles.filecreatorbundles;

import java.util.ListResourceBundle;

public class FilterCreatorFormRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"filterColumnLabel", "Odaberite stupac za filtriranje:"},
                {"signLabel", "Odaberite znak:"},
                {"valueForFilteringLabel", "Unesite vrijednost za filtriranje:"},
                {"columnsForFilteringComboBox", "stupci"},
                {"signsCombobox", "znakovi"},
                {"filteringValueTextField", "ovdje unesite vrijednost"},
                {"createButton", "Stvori"},
                {"cancelButton", "Odustani"}
        };
        return content;
    }
}
