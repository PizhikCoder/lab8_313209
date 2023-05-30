package client.UI.resourcebundles.mainformbundles;

import java.util.ListResourceBundle;

public class MainFormRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"createFilterButton", "Создать фильтр"},
                {"removeFiltersButton", "Удалить фильтры"},
                {"addButton", "Добавить"},
                {"addIfMinButton", "Добавить если минимально"},
                {"updateButton", "Обновить"},
                {"removeButton", "Удалить"},
                {"removeByIdButton", "Удалить по id"},
                {"clearButton", "Очистить"},
                {"filterLessThanFrontManButton", "Фильтровать меньших чем солист"},
                {"countGreaterThanFrontManButton", "Количество больших чем солист"},
                {"groupCountingByCoordinatesButton", "Группировать по координатам"},
                {"controllersLabel", "Контроллеры"},
                {"infoMenu", "Инфо"},
                {"settingsMenu", "Настройки"},
                {"helpMenu", "Помощь"},
                {"languageMenuItem", "Язык"},
                {"logOutMenuItem", "Выйти"},
                {"executeScriptButton", "Запустить скрипт"},
                {"visualizeButton", "Визуализация"}
        };
        return contents;
    }
}
