package client.UI.resourcebundles.commandsanswersbundles;


import java.util.ListResourceBundle;

public class CommandsAnswersRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"addCommandExecuted", "Objekt loodi!"},
                {"addCommandNotExecuted", "Objekti ei loodud!"},
                {"addIfMinCommandExecuted", "Objekt loodi!"},
                {"addIfMinCommandNotExecuted", "Objekti ei loodud!"},
                {"clearCommandExecuted", "Mudelid tühjendati!"},
                {"executeScriptCommandExecuted", "Skript käivitati!"},
                {"executeScriptRecursionDetected", "Avastati rekursioon! Skripti käivitamine katkestati."},
                {"executeScriptCanNotAccessToFile", "Failile %s juurdepääs pole võimalik!"},
                {"executeScriptCanNotFoundFile", "Faili %s ei leitud!"},
                {"removeByIdCommandExecuted", "Objekt eemaldati!"},
                {"removeByIdCommandNotExecuted", "Objekti ei eemaldatud!"},
                {"signInCommandExecuted", "Olete autoriseeritud!"},
                {"signInCommandNotExecuted", "Vale kasutajanimi või parool!"},
                {"signUpCommandExecuted", "Konto registreeriti!"},
                {"signUpCommandNotExecuted", "Kontot ei registreeritud!"},
                {"updateCommandExecuted", "Objekt uuendati!"},
                {"updateCommandNotExecuted", "Valitud objekt kuulub teisele kasutajale!"},
                {"countGreaterThanFrontManExecuted", "Muusikabändid, mille esiliige on kõrgem kui %s: %s"},
                {"countGreaterThanFrontManNotExecuted", "Sisestatud pikkus on valel kujul! (Oodatud: 0<ujuk<=240)"},
                {"groupCountingByCoordinatesExecuted", "\nKoordinaatide rühm: %s\nElementide arv: %s\n"},
                {"collectionIsEmpty", "Kollektsioon on tühi!"},
                {"addIfMinCommandIdInWrongFormat", "Vale formaadis sisestatud ID!"},
                {"addIfMinCommandIdIsNotMin", "Sisestatud ID ei ole minimaalne!"},
                {"infoCommandExecuted", "Kogumise info:" +
                        "\n---Tüüp: Muusika bänd" +
                        "\n---Elementide arv: %s"},
                {"idDoesNotExist", "Sisestatud ID ei eksisteeri!"}
        };
        return content;
    }
}
