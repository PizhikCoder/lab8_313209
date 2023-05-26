package client.UI.resourcebundles.authorizationformbundles;

import java.util.ListResourceBundle;

public class AuthorizationFormRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"logInTextField", "Unesite korisničko ime ovdje"},
                {"passwordTextField", "Unesite lozinku ovdje"},
                {"settingsMenu", "Postavke"},
                {"languageMenuItem", "Jezik"},
                {"loginLabel", "Korisničko ime"},
                {"passwordLabel", "Lozinka"},
                {"signInButton", "Prijavi se"},
                {"signUpButton", "Registriraj se"}
        };
        return contents;
    }
}
