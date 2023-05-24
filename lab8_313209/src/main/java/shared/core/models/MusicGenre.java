package shared.core.models;

import client.UI.Controllers.MainFormController;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ResourceBundle;

public enum MusicGenre implements Serializable {
    ROCK("rock"),
    BLUES("blues"),
    POP("pop"),
    POST_ROCK("postRock");
    private final String bundleObjectName;

    MusicGenre (String bundleObjectName){
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("client.UI.resourcebundles.MusicGenreRB", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
