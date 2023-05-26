package client.backend.connection;

import client.UI.resourcebundles.enums.RuntimeOutputs;
import shared.connection.interfaces.IRequest;
import shared.connection.requests.AuthorizationRequest;
import shared.connection.requests.CommandRequest;
import shared.connection.requests.ValidationRequest;
import shared.interfaces.IPrinter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PipedInputStream;

public class ThreadsBridgeHandler {
    private final static int BASE_ID_VALUE = -1;

    /**
     * Waits for an IValidationRequest response from another thread and returns it.
     * @param pipedInputStream
     * @param printer
     * @return
     */
    public static boolean getValidationResponse(PipedInputStream pipedInputStream, IPrinter printer){
        try {
            ValidationRequest request = (ValidationRequest) new ObjectInputStream(pipedInputStream).readObject();
            return (boolean)request.getData();
        } catch (IOException e) {
            printer.print(RuntimeOutputs.CONNECTION_COULD_NOT_BE_ESTABLISHED.toString());
        } catch (ClassNotFoundException e) {
            printer.print(RuntimeOutputs.CONNECTION_UNKNOWN_CLASS_RECEIVED.toString());
        }
        return false;
    }

    /**
     * Waits for an CommandRequest response from another thread and returns it.
     * @param pipedInputStream
     * @param printer
     * @return
     */
    public static boolean waitCommandExecuted(PipedInputStream pipedInputStream, IPrinter printer){
        try {
            CommandRequest request = (CommandRequest) new ObjectInputStream(pipedInputStream).readObject();
            return (boolean)request.getData();
        } catch (IOException e) {
            printer.print(RuntimeOutputs.CONNECTION_COULD_NOT_BE_ESTABLISHED.toString());
        } catch (ClassNotFoundException e) {
            printer.print(RuntimeOutputs.CONNECTION_UNKNOWN_CLASS_RECEIVED.toString());
        }
        return false;
    }

    /**
     * Waits for an AuthorizationRequest response from another thread and returns it.
     * @param pipedInputStream
     * @param printer
     * @return
     */
    public static AuthorizationRequest getAuthorizationResponse(PipedInputStream pipedInputStream, IPrinter printer){
        try {
            AuthorizationRequest request = (AuthorizationRequest) new ObjectInputStream(pipedInputStream).readObject();
            return request;
        } catch (IOException e) {
            printer.print(RuntimeOutputs.CONNECTION_COULD_NOT_BE_ESTABLISHED.toString());
        } catch (ClassNotFoundException e) {
            printer.print(RuntimeOutputs.CONNECTION_UNKNOWN_CLASS_RECEIVED.toString());
        }
        return new AuthorizationRequest(null,null, BASE_ID_VALUE, false);
    }
}
