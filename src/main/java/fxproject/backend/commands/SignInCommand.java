package fxproject.backend.commands;

import fxproject.backend.connection.ServerListenerThread;
import fxproject.backend.connection.ThreadsBridgeHandler;
import fxproject.backend.core.*;
import shared.connection.requests.AuthorizationRequest;
import shared.core.ClientInfo;
import shared.core.exceptions.*;
import shared.interfaces.INoAuthCommand;

public class SignInCommand extends Command implements INoAuthCommand {
    private final Invoker invoker;
    private final String login;
    private final String password;

    public SignInCommand(Invoker invoker, String login, String password){
        this.invoker = invoker;
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean execute(String... args) {
        if (ClientInfo.isAuthorized()){
            return true;
        }
        invoker.getConnection().getSender().send(new AuthorizationRequest(login, password, false));
        if (ThreadsBridgeHandler.getAuthorizationResponse(invoker.getPipedInputStream(), invoker.getPrinter())){
            ClientInfo.setIsAuthorized(true);
            ClientInfo.setLogin(login);
            ClientInfo.setPassword(password);
            try{
                ServerListenerThread.getLocker().lock();
                ServerListenerThread.getCondition().signal();
            }
            finally {
                ServerListenerThread.getLocker().unlock();
            }
            return true;
        }
        return false;
    }
}
