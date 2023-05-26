package client.UI.resourcebundles.mainformbundles;

import java.util.ListResourceBundle;

public class MainFormRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"createFilterButton", "Crear filtro"},
                {"removeFiltersButton", "Eliminar filtros"},
                {"addButton", "Agregar"},
                {"addIfMinButton", "Agregar si es el mínimo"},
                {"updateButton", "Actualizar"},
                {"removeButton", "Eliminar"},
                {"removeByIdButton", "Eliminar por ID"},
                {"clearButton", "Limpiar"},
                {"filterLessThanFrontManButton", "Filtrar menos que líder"},
                {"countGreaterThanFrontManButton", "Contar mayor que líder"},
                {"groupCountingByCoordinatesButton", "Agrupar conteo por coordenadas"},
                {"controllersLabel", "Controladores"},
                {"infoMenu", "Información"},
                {"settingsMenu", "Configuración"},
                {"helpMenu", "Ayuda"},
                {"languageMenuItem", "Idioma"},
                {"logOutMenuItem", "Cerrar sesión"},
                {"executeScriptButton", "Ejecutar script"}
        };
        return contents;
    }
}
