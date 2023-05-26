package client.UI.resourcebundles.authorizationformbundles;

import java.util.ListResourceBundle;

public class AuthorizationFormRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"logInTextField", "Sisesta siia kasutajanimi"},
                {"passwordTextField", "Sisesta siia parool"},
                {"settingsMenu", "Seaded"},
                {"languageMenuItem", "Keel"},
                {"loginLabel", "Kasutajanimi"},
                {"passwordLabel", "Parool"},
                {"signInButton", "Logi sisse"},
                {"signUpButton", "Registreeri"}
        };
        return contents;
    }
}
