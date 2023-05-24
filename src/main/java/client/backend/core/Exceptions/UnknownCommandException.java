package client.backend.core.Exceptions;

public class UnknownCommandException extends Exception{
    public UnknownCommandException(String receivedCommandName){
        super(String.format("Unknown command: %s", receivedCommandName));
    }
}
