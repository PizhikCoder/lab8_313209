package shared.connection.requests;

import shared.commands.commandsdtos.CommandDTO;
import shared.connection.interfaces.ICommandRequest;
import shared.core.ClientInfo;

import java.io.Serializable;

public class CommandRequest implements ICommandRequest, Serializable {
    private String login;
    private String password;
    private CommandDTO command;
    private Object data;

    public CommandRequest(CommandDTO command, Object data){
        this.command = command;
        this.data = data;
        this.login = ClientInfo.getLogin();
        this.password = ClientInfo.getPassword();
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public CommandDTO getCommand() {
        return command;
    }
}
