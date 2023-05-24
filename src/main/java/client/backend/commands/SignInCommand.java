package client.backend.commands;

import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.connection.ServerListenerThread;
import client.backend.connection.ThreadsBridgeHandler;
import client.backend.core.*;
import shared.connection.requests.AuthorizationRequest;
import shared.core.ClientInfo;
import shared.interfaces.INoAuthCommand;

public class SignInCommand extends Command implements INoAuthCommand {
    private final int BASE_ID_VALUE = -1;
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
        try{
            if (ClientInfo.isAuthorized()){
                return true;
            }
            invoker.getConnection().getSender().send(new AuthorizationRequest(login, password, BASE_ID_VALUE, false));
            AuthorizationRequest authorizationRequest = ThreadsBridgeHandler.getAuthorizationResponse(invoker.getPipedInputStream(), invoker.getPrinter());
            if (authorizationRequest.getData()){
                ClientInfo.setIsAuthorized(true);
                ClientInfo.setLogin(login);
                ClientInfo.setPassword(password);
                ClientInfo.setUserId(authorizationRequest.getId());
                invoker.getPrinter().print(CommandsAnswers.SIGN_IN_COMMAND_EXECUTED.toString());
                return true;
            }
            invoker.getPrinter().print(CommandsAnswers.SIGN_IN_COMMAND_NOT_EXECUTED.toString());
            return false;
        }
        finally {
            unlockWaitingConnectionThread();
        }
    }

    private void unlockWaitingConnectionThread(){
        try {
            ServerListenerThread.getLocker().lock();
            ServerListenerThread.getCondition().signal();
        }
        finally {
            ServerListenerThread.getLocker().unlock();
        }
    }
}
