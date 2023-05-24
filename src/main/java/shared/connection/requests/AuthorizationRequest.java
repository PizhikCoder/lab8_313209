package shared.connection.requests;

import shared.connection.interfaces.IAuthRequest;
import shared.connection.interfaces.IRequest;

public class AuthorizationRequest implements IAuthRequest {
    private boolean data;
    private String login;
    private int userId;
    private String password;

    public AuthorizationRequest(String login, String password, int userId, boolean data){
        this.login = login;
        this.password = password;
        this.userId = userId;
        this.data = data;
    }

    @Override
    public Boolean getData() {
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

    public int getId(){
        return userId;
    }
}
