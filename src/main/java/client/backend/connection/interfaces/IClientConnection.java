package client.backend.connection.interfaces;

import client.backend.core.*;
import shared.connection.interfaces.IRequest;
import shared.core.exceptions.ConnectionException;

/**
 * Client connection.
 */
public interface IClientConnection {
    boolean connect(Invoker invoker) throws ConnectionException;

    IRequest getResponse() throws ConnectionException;

    IMessageSender getSender();

    boolean isConnected();

}
