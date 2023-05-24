package client.backend.connection.interfaces;

import shared.connection.interfaces.IRequest;

public interface IMessageSender {
    void send(IRequest data);
}
