package fxproject.backend.connection.interfaces;

import fxproject.backend.core.*;
import shared.connection.interfaces.IRequest;
import shared.core.exceptions.ConnectionException;

import java.net.ConnectException;

/**
 * Client connection.
 */
public interface IClientConnection {
    boolean connect(Invoker invoker) throws ConnectionException;

    IRequest getResponse() throws ConnectionException;

    IMessageSender getSender();

    boolean isConnected();

}
