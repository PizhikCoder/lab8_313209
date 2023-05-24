package shared.core.models;

import client.UI.Controllers.MainFormController;

import java.io.Serializable;
import java.util.ResourceBundle;

public enum Country implements Serializable {
    RUSSIA("russia"),
    GERMANY("germany"),
    VATICAN("vatican"),
    ITALY("italy");
    private final String bundleObjectName;

    Country (String bundleObjectName){
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("client.UI.resourcebundles.CountryRB", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
