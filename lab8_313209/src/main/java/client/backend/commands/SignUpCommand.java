package client.backend.commands;


import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.connection.ServerListenerThread;
import client.backend.connection.ThreadsBridgeHandler;
import client.backend.core.Invoker;
import shared.connection.requests.AuthorizationRequest;
import shared.connection.requests.RegistrationRequest;
import shared.core.ClientInfo;
import shared.interfaces.INoAuthCommand;

public class SignUpCommand extends Command implements INoAuthCommand {
    private String login;
    private String password;
    private Invoker invoker;
    public SignUpCommand(Invoker invoker, String login, String password){
        this.invoker = invoker;
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean execute(String... args) {
        try{
            invoker.getConnection().getSender().send(new RegistrationRequest(login, password, false));
            AuthorizationRequest authorizationRequest = ThreadsBridgeHandler.getAuthorizationResponse(invoker.getPipedInputStream(), invoker.getPrinter());
            if (authorizationRequest.getData()){
                ClientInfo.setIsAuthorized(true);
                ClientInfo.setLogin(login);
                ClientInfo.setPassword(password);
                ClientInfo.setUserId(authorizationRequest.getId());
                invoker.getPrinter().print(CommandsAnswers.SIGN_UP_COMMAND_EXECUTED.toString());
                return true;
            }
            invoker.getPrinter().print(CommandsAnswers.SIGN_UP_COMMAND_NOT_EXECUTED.toString());
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
