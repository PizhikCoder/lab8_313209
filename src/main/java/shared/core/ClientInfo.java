package shared.core;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Contains auth info about current client.
 */
public class ClientInfo {
    private static String login;

    private static String password;

    private static volatile AtomicBoolean isAuthorized = new AtomicBoolean(false);

    public static void setLogin(String login) {
        ClientInfo.login = login;
    }

    public static void setPassword(String password) {
        ClientInfo.password = password;
    }

    public static boolean isAuthorized() {
        return isAuthorized.get();
    }

    public static void setIsAuthorized(boolean isAuthorized) {
        ClientInfo.isAuthorized.set(isAuthorized);
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }
}
