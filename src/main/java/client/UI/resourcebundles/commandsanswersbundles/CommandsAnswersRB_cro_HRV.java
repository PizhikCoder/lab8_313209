package client.UI.resourcebundles.commandsanswersbundles;


import java.util.ListResourceBundle;

public class CommandsAnswersRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"addCommandExecuted", "Objekt je stvoren!"},
                {"addCommandNotExecuted", "Objekt nije stvoren!"},
                {"addIfMinCommandExecuted", "Objekt je stvoren!"},
                {"addIfMinCommandNotExecuted", "Objekt nije stvoren!"},
                {"clearCommandExecuted", "Modeli su očišćeni!"},
                {"executeScriptCommandExecuted", "Skripta izvršena!"},
                {"executeScriptRecursionDetected", "Otkrivena rekurzija! Izvršavanje skripte prekinuto."},
                {"executeScriptCanNotAccessToFile", "Nemoguć pristup datoteci: %s!"},
                {"executeScriptCanNotFoundFile", "Datoteka: %s ne postoji!"},
                {"removeByIdCommandExecuted", "Objekt uklonjen!"},
                {"removeByIdCommandNotExecuted", "Objekt nije uklonjen!"},
                {"signInCommandExecuted", "Uspješno ste autorizirani!"},
                {"signInCommandNotExecuted", "Pogrešno korisničko ime ili lozinka!"},
                {"signUpCommandExecuted", "Račun je registriran!"},
                {"signUpCommandNotExecuted", "Račun nije registriran!"},
                {"updateCommandExecuted", "Objekt je ažuriran!"},
                {"updateCommandNotExecuted", "Odabrani objekt pripada drugom korisniku!"},
                {"countGreaterThanFrontManExecuted", "Glazbeni sastavi s vođom visokim više od %s: %s"},
                {"countGreaterThanFrontManNotExecuted", "Unesena visina je u pogrešnom formatu! (Očekivano: float)"},
                {"groupCountingByCoordinatesExecuted", "\nGrupa koordinata: %s\nBroj elemenata: %s\n"},
                {"collectionIsEmpty", "Kolekcija je prazna!"},
                {"addIfMinCommandIdInWrongFormat", "Uneseni ID je u pogrešnom formatu!"},
                {"addIfMinCommandIdIsNotMin", "Uneseni ID nije minimalan!"},
                {"infoCommandExecuted", "Informacije o kolekciji:" +
                        "\n---Tip: Glazbeni bend" +
                        "\n---Broj elemenata: %s"},
                {"idDoesNotExist", "Uneseni ID ne postoji!"},
        };
        return content;
    }
}
