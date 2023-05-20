package fxproject.backend.core;

import fxproject.backend.commands.Command;
import fxproject.backend.connection.interfaces.IClientConnection;
import shared.core.ClientInfo;
import shared.core.exceptions.*;
import shared.interfaces.INoAuthCommand;
import shared.interfaces.IPrinter;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Invoker {
    private final IPrinter printer;

    private static Invoker invoker;

    private final IClientConnection connection;

    private final PipedInputStream pipedInputStream;

    private Invoker(IPrinter printer, PipedOutputStream pipedOutputStream, IClientConnection connection) throws IOException {
        this.printer = printer;
        this.pipedInputStream = new PipedInputStream();
        this.pipedInputStream.connect(pipedOutputStream);
        this.connection = connection;
    }

    public static Invoker create(IPrinter printer, PipedOutputStream pipedOutputStream, IClientConnection connection) throws IOException {
        if (invoker == null){
            invoker = new Invoker( printer, pipedOutputStream, connection);
            return invoker;
        }
        return invoker;
    }

    public static Invoker getInstance(){
        return invoker;
    }

    /**
     * Invoke command logic.
     * @param command command's object.
     * @param arguments command's arguments.
     */
    public boolean invokeCommand(Command command, String ... arguments){
        try{
            if (command != null){
                if (!connection.isConnected() && (ClientInfo.isAuthorized() || command instanceof INoAuthCommand)){
                    return command.execute(arguments);
                }
            }
        }
        catch (RecursionException exception){
            printer.print("Recursion exception.");
            printer.print(exception.getMessage());
        }

        catch (CommandParamsException exception){
            printer.print("Params exception.");
            printer.print(exception.getMessage());
        }
        catch (FileDoesNotExistException exception){
            printer.print("File exception.");
            printer.print(exception.getMessage());
        }
        catch (ArgumentLimitsException exception){
            printer.print("Argument limits exception.");
            printer.print(exception.getMessage());
        }
        catch (FileAccessException exception){
            printer.print("Command exception.");
            printer.print(exception.getMessage());
        }
        return false;
    }


    public IPrinter getPrinter() {
        return printer;
    }

    public IClientConnection getConnection(){
        return connection;
    }

    public PipedInputStream getPipedInputStream(){
        return pipedInputStream;
    }
}
