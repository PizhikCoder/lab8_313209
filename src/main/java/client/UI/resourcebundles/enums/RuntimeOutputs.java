package client.UI.resourcebundles.enums;

import client.UI.Controllers.MainFormController;

import java.util.ResourceBundle;

public enum RuntimeOutputs {
    UNKNOWN_COMMAND_EXCEPTION_OUTPUT("unknownCommandExceptionOutput"),
    FILE_LISTENER_CAN_NOT_READ_FILE("fileListenerCanNotReadFromFile"),
    FILE_LISTENER_PROBLEM_WITH_SCRIPT_FILE("fileListenerProblemWithScriptFile"),
    CONNECTION_UNKNOWN_HOST_NAME("connectionUnknownHostName"),
    CONNECTION_UNKNOWN_CLASS_RECEIVED("connectionUnknownClassReceived"),
    CONNECTION_COULD_NOT_BE_ESTABLISHED("connectionCouldNotBeEstablished"),
    CONNECTION_RECONNECTING("connectionReconnecting"),
    CONNECTION_CAN_NOT_CLOSE_CHANNEL("canNotCloseChannelWithServer"),
    CONNECTION_RECONNECTED("connectionReconnected"),
    CONNECTION_RECONNECTION_FAILED("connectionReconnectionFailed"),
    TCP_SENDER_CAN_NOT_SEND_REQUEST("tcpSenderCanNOtSendRequest"),
    CAN_NOT_INIT_COMPONENT("canNonInitComponent");
    private final String bundleObjectName;

    RuntimeOutputs (String bundleObjectName){
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("client.UI.resourcebundles.runtimeoutputsbundles.RuntimeOutputRB", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
