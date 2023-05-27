package client.backend.core;

import client.backend.commands.Command;
import client.backend.connection.interfaces.IClientConnection;
import shared.core.ClientInfo;
import shared.core.exceptions.*;
import shared.interfaces.INoAuthCommand;
import shared.interfaces.IPrinter;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Invoker {
    private final IPrinter printer;

    private static Invoker invoker;

    private final IClientConnection connection;

    private final PipedInputStream pipedInputStream;

    private final ExecutorService threadsPool;

    private Invoker(IPrinter printer, PipedOutputStream pipedOutputStream, IClientConnection connection) throws IOException {
        this.printer = printer;
        this.pipedInputStream = new PipedInputStream();
        this.pipedInputStream.connect(pipedOutputStream);
        this.connection = connection;
        threadsPool = Executors.newCachedThreadPool(runnable->{
            final Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });
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
    public Future<Boolean> invokeCommand(Command command, String ... arguments){
        return threadsPool.submit(()->{
        try{
            if (command != null){
                if (!connection.isConnected() && (ClientInfo.isAuthorized() || command instanceof INoAuthCommand)){
                    return command.execute(arguments);
                }
            }
        }
        catch (CommandParamsException exception){
            printer.print(exception.getMessage());
        }
        return false;
        });
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
