package client.UI.resourcebundles.authorizationformbundles;

import java.util.ListResourceBundle;

public class AuthorizationFormRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"logInTextField", "Ingrese el usuario aquí"},
                {"passwordTextField", "Ingrese la contraseña aquí"},
                {"settingsMenu", "Configuración"},
                {"languageMenuItem", "Idioma"},
                {"loginLabel", "Usuario"},
                {"passwordLabel", "Contraseña"},
                {"signInButton", "Iniciar sesión"},
                {"signUpButton", "Registrarse"}
        };
        return contents;
    }
}
