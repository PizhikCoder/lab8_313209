package shared.connection.requests;

import shared.commands.commandsdtos.CommandDTO;
import shared.connection.interfaces.ICommandRequest;

import java.io.Serializable;

public class ValidationRequest implements ICommandRequest, Serializable {
    private CommandDTO command;
    private Object data;

    public ValidationRequest(CommandDTO command, Object data) {
        this.command = command;
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    /**
     * @return always returns null in ValidationRequest
     */
    @Override
    public String getLogin() {
        return null;
    }

    /**
     * @return always returns null in ValidationRequest
     */
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public CommandDTO getCommand() {
        return command;
    }
}
