package shared.connection.interfaces;

public interface IAuthRequest extends IRequest{
    String getLogin();

    String getPassword();
}
