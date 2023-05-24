package client.UI.resourcebundles.enums;


public enum MainFormElements {
    SETTINGS_MENU("settingsMenu"),
    LOG_OUT_MENU_ITEM("logOutMenuItem"),
    LANGUAGE_MENU_ITEM("languageMenuItem"),
    HELP_MENU("helpMenu"),
    INFO_MENU("infoMenu"),
    CREATE_FILTER_BUTTON("createFilterButton"),
    REMOVE_FILTERS_BUTTON("removeFiltersButton"),
    ADD_BUTTON("addButton"),
    ADD_IF_MIN_BUTTON("addIfMinButton"),
    UPDATE_BUTTON("updateButton"),
    REMOVE_BUTTON("removeButton"),
    REMOVE_BY_ID_BUTTON("removeByIdButton"),
    CLEAR_BUTTON("clearButton"),
    FILTER_LESS_THAN_FRONT_MAN_BUTTON("filterLessThanFrontManButton"),
    COUNT_GREATER_THAN_FRONT_MAN_BUTTON("countGreaterThanFrontManButton"),
    GROUP_COUNTING_BY_COORDINATES_BUTTON("groupCountingByCoordinatesButton"),
    CONTROLLERS_LABEL("controllersLabel"),
    EXECUTE_SCRIPT_BUTTON("executeScriptButton");
    private final String bundleObjectName;

    MainFormElements (String bundleObjectName){
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        return bundleObjectName;
    }
}
