package client.UI.resourcebundles.mainformbundles;

import javafx.event.ActionEvent;

import java.util.ListResourceBundle;

public class MainFormRB_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"createFilterButton", "Create filter"},
                {"removeFiltersButton", "Remove filters"},
                {"addButton", "Add"},
                {"addIfMinButton", "Add if min"},
                {"updateButton", "Update"},
                {"removeButton", "Remove"},
                {"removeByIdButton", "Remove by id"},
                {"clearButton", "clear"},
                {"filterLessThanFrontManButton", "Filter less than front man"},
                {"countGreaterThanFrontManButton", "Count greater than front man"},
                {"groupCountingByCoordinatesButton", "Group counting by coordinates"},
                {"controllersLabel", "Controllers"},
                {"infoMenu", "Info"},
                {"settingsMenu", "Settings"},
                {"helpMenu", "Help"},
                {"languageMenuItem", "Language"},
                {"logOutMenuItem", "Log out"},
                {"executeScriptButton", "Execute script"},
                {"visualizeButton", "Visualize"}
        };
        return contents;
    }
}
