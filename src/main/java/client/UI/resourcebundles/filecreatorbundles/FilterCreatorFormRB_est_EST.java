package client.UI.resourcebundles.filecreatorbundles;

import java.util.ListResourceBundle;

public class FilterCreatorFormRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"filterColumnLabel", "Valige filtreerimise veerg:"},
                {"signLabel", "Valige märk:"},
                {"valueForFilteringLabel", "Sisestage filtreerimiseks väärtus:"},
                {"columnsForFilteringComboBox", "veerud"},
                {"signsCombobox", "märgid"},
                {"filteringValueTextField", "sisestage siia väärtus"},
                {"createButton", "Loo"},
                {"cancelButton", "Tühista"}
        };
        return content;
    }
}
