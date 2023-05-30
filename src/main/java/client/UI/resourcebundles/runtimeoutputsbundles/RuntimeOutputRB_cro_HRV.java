package client.UI.resourcebundles.runtimeoutputsbundles;

import java.util.ListResourceBundle;

public class RuntimeOutputRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"unknownCommandExceptionOutput", "Nepoznata naredba: %s"},
                {"fileListenerCanNotReadFromFile", "Ne mogu čitati iz datoteke!"},
                {"fileListenerProblemWithScriptFile", "Nešto nije u redu s datotekom skripte..."},
                {"connectionUnknownHostName", "Nepoznato ime računala!"},
                {"connectionUnknownClassReceived", "Primljen je nepoznat razred!"},
                {"connectionCouldNotBeEstablished", "Veza nije mogla biti uspostavljena!"},
                {"connectionReconnecting", "Ponovno povezivanje..."},
                {"canNotCloseChannelWithServer", "Ne mogu zatvoriti kanal s poslužiteljem!"},
                {"connectionReconnected", "Ponovno povezan!"},
                {"connectionReconnectionFailed", "Ponovno povezivanje nije uspjelo!"},
                {"tcpSenderCanNOtSendRequest", "Ne mogu poslati zahtjev!"},
                {"canNonInitComponent", "Ne mogu inicijalizirati komponentu!"},
                {"anyFieldsDoesNotValid", "Nijedno polje nije valjano!"},
                {"modelWasNotSelectedInTable", "Odaberite model u tablici."},
                {"columnWasNotSelected", "Odaberite stupac!"},
                {"signWasNotSelected", "Odaberite znak!"},
                {"valueWasNotEntered", "Unesite vrijednost!"}
        };
        return content;
    }
}
