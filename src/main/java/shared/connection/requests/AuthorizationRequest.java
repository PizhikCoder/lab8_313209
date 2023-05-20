package shared.connection.requests;

import shared.connection.interfaces.IAuthRequest;
import shared.connection.interfaces.IRequest;

public class AuthorizationRequest implements IAuthRequest {
    private Boolean data;
    private String login;

    private String password;

    public AuthorizationRequest(String login, String password, Boolean data){
        this.login = login;
        this.password = password;
        this.data = data;
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
}
