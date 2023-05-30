package client.UI.resourcebundles.runtimeoutputsbundles;

import java.util.ListResourceBundle;

public class RuntimeOutputRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"unknownCommandExceptionOutput", "Tundmatu käsk: %s"},
                {"fileListenerCanNotReadFromFile", "Ei saa lugeda failist!"},
                {"fileListenerProblemWithScriptFile", "Midagi läks valesti skriptifailiga..."},
                {"connectionUnknownHostName", "Tundmatu hostinimi!"},
                {"connectionUnknownClassReceived", "Vastu võeti tundmatu klass!"},
                {"connectionCouldNotBeEstablished", "Ühendust ei saanud luua!"},
                {"connectionReconnecting", "Ühenduse loomine..."},
                {"canNotCloseChannelWithServer", "Ei saa sulgeda kanalit serveriga!"},
                {"connectionReconnected", "Ühendus taastatud!"},
                {"connectionReconnectionFailed", "Ühenduse taastamine ebaõnnestus!"},
                {"tcpSenderCanNOtSendRequest", "Ei saa päringut saata!"},
                {"canNonInitComponent", "Komponenti ei saa algatada!"},
                {"anyFieldsDoesNotValid", "Ükski väli ei ole kehtiv!"},
                {"modelWasNotSelectedInTable", "Valige tabelis mudel."},
                {"columnWasNotSelected", "Valige veerg!"},
                {"signWasNotSelected", "Valige märk!"},
                {"valueWasNotEntered", "Sisestage väärtus!"}
        };
        return content;
    }
}
