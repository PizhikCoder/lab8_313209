package client.backend.core.Exceptions;

import client.UI.resourcebundles.enums.RuntimeOutputs;

public class UnknownCommandException extends Exception{
    public UnknownCommandException(String receivedCommandName){
        super(String.format(RuntimeOutputs.UNKNOWN_COMMAND_EXCEPTION_OUTPUT.toString(), receivedCommandName));
    }
}
