package client.UI.resourcebundles.runtimeoutputsbundles;

import java.util.ListResourceBundle;

public class RuntimeOutputRB_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"unknownCommandExceptionOutput", "Unknown command: %s"},
                {"fileListenerCanNotReadFromFile", "Can not read from file!"},
                {"fileListenerProblemWithScriptFile", "Something went wrong with script file..."},
                {"connectionUnknownHostName", "Unknown host name!"},
                {"connectionUnknownClassReceived", "Unknown class has been received!"},
                {"connectionCouldNotBeEstablished", "Connection could not be established!"},
                {"connectionReconnecting", "Reconnecting..."},
                {"canNotCloseChannelWithServer", "Can not close channel with server!"},
                {"connectionReconnected", "Reconnected!"},
                {"connectionReconnectionFailed", "Reconnection failed!"},
                {"tcpSenderCanNOtSendRequest", "Can not send request!"},
                {"canNonInitComponent", "Can not initialize component!"},
                {"anyFieldsDoesNotValid", "Any fields does not valid!"},
                {"modelWasNotSelectedInTable", "Select model in table."},
                {"columnWasNotSelected", "Select column!"},
                {"signWasNotSelected", "Select sign!"},
                {"valueWasNotEntered", "Enter value!"}
        };
        return content;
    }
}
