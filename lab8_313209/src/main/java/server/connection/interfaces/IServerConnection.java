package server.connection.interfaces;

import shared.connection.interfaces.IRequest;
import shared.connection.interfaces.IServerBackRequest;
import shared.core.models.MusicBand;

import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 * Server connection
 */
public interface IServerConnection {
    Future send(IRequest data);
    void sendChangesToAll(IServerBackRequest iServerBackRequest);
}
