package client.UI.resourcebundles.enums;

import client.UI.Controllers.MainFormController;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public enum AvailableLocales {
    ENGLISH(new Locale("en", "US"), "english", ZoneId.of("US/Central")),
    RUSSIAN(new Locale("ru", "RU"), "russian", ZoneId.of("Europe/Moscow"));

    private Locale locale;

    private final String bundleObjectName;

    private final ZoneId zoneId;

    AvailableLocales(Locale locale, String bundleObjectName, ZoneId zoneId){
        this.locale = locale;
        this.bundleObjectName = bundleObjectName;
        this.zoneId = zoneId;
    }

    public Locale getLocale(){
        return locale;
    }

    public ZoneId getZoneID(){
        return zoneId;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("client.UI.resourcebundles.AvailableLocalesRB", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
