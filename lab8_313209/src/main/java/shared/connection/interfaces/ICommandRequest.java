package shared.connection.interfaces;

import shared.commands.commandsdtos.CommandDTO;

public interface ICommandRequest extends IRequest{
    String getLogin();

    String getPassword();

    CommandDTO getCommand();
}
