package server.core.printers;

import server.connection.interfaces.IServerConnection;
import shared.connection.requests.MessageRequest;
import shared.interfaces.IPrinter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Sends a text message.
 */
public class SocketPrinter implements IPrinter {

    private IServerConnection clientConnection;

    public SocketPrinter(IServerConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void print(String data) {
        Future res = clientConnection.send(new MessageRequest("[SERVER]: "+data));
        try {
            res.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
