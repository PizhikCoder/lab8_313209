package client.UI.resourcebundles.runtimeoutputsbundles;

import java.util.ListResourceBundle;

public class RuntimeOutputRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"unknownCommandExceptionOutput", "Comando desconocido: %s"},
                {"fileListenerCanNotReadFromFile", "No se puede leer desde el archivo"},
                {"fileListenerProblemWithScriptFile", "Hubo un problema con el archivo de script"},
                {"connectionUnknownHostName", "Nombre de host desconocido"},
                {"connectionUnknownClassReceived", "Se ha recibido una clase desconocida"},
                {"connectionCouldNotBeEstablished", "No se pudo establecer la conexión"},
                {"connectionReconnecting", "Reconectando..."},
                {"canNotCloseChannelWithServer", "No se puede cerrar el canal con el servidor"},
                {"connectionReconnected", "Reconectado"},
                {"connectionReconnectionFailed", "Error al reconectar"},
                {"tcpSenderCanNOtSendRequest", "No se puede enviar la solicitud"},
                {"canNonInitComponent", "No se puede inicializar el componente"},
                {"anyFieldsDoesNotValid", "¡Ningún campo es válido!"},
                {"modelWasNotSelectedInTable", "Seleccione un modelo en la tabla."},
                {"columnWasNotSelected", "¡Seleccione una columna!"},
                {"signWasNotSelected", "¡Seleccione un signo!"},
                {"valueWasNotEntered", "¡Ingrese un valor!"}
        };
        return content;
    }
}
