package client.UI.resourcebundles.filecreatorbundles;

import java.util.ListResourceBundle;

public class FilterCreatorFormRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"filterColumnLabel", "Seleccionar columna de filtrado:"},
                {"signLabel", "Seleccionar signo:"},
                {"valueForFilteringLabel", "Ingresar valor para filtrar:"},
                {"columnsForFilteringComboBox", "columnas"},
                {"signsCombobox", "signos"},
                {"filteringValueTextField", "valor aquí"},
                {"createButton", "Crear"},
                {"cancelButton", "Cancelar"}
        };
        return content;
    }
}
