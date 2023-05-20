package shared.connection.requests;

import shared.connection.interfaces.IAuthRequest;

public class RegistrationRequest implements IAuthRequest {
    private Boolean data;
    private String login;

    private String password;

    public RegistrationRequest(String login, String password, Boolean data){
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
