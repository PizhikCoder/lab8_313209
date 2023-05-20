package fxproject.backend.connection;

import fxproject.UI.Controllers.MainFormController;
import fxproject.backend.connection.interfaces.IClientConnection;
import fxproject.backend.tableHandlers.TableViewHandler;
import javafx.application.Platform;
import shared.connection.interfaces.IRequest;
import shared.connection.interfaces.IServerBackRequest;
import shared.connection.requests.*;
import shared.core.ClientInfo;
import shared.core.exceptions.ConnectionException;
import shared.core.models.MusicBand;
import shared.interfaces.IPrinter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Stream class for background server wiretapping.
 */
public class ServerListenerThread extends Thread{

    private PipedOutputStream pipedOutputStream;

    private IClientConnection connection;

    private IPrinter printer;

    private final static ReentrantLock locker = new ReentrantLock();

    private final static Condition condition = locker.newCondition();

    /**
     *
     * @param stream A stream for transferring data to the main thread.
     * @param connection
     * @param printer
     */
    public ServerListenerThread(PipedOutputStream stream, IClientConnection connection, IPrinter printer){
        pipedOutputStream = stream;
        this.connection = connection;
        this.printer = printer;
    }


    public void run(){
        startListening();
    }

    private void startListening(){
        while(!isInterrupted()){
            try{
                IRequest response = connection.getResponse();
                if (response instanceof MessageRequest){
                    handleMessageRequest(response);
                }
                else if(response instanceof ValidationRequest){
                    if (response.getData() == null) continue;
                    handleValidationRequest(response);
                }
                else if(response instanceof CommandRequest){
                    handleCommandRequest(response);
                }
                else if(response instanceof AuthorizationRequest){
                    try{
                        locker.lock();
                        handleAuthorizationRequest(response);
                        condition.await();
                    }
                    catch (InterruptedException interruptedException){}
                    finally {
                        locker.unlock();
                    }
                }
                else if (response instanceof CollectionRequest && ClientInfo.isAuthorized()){
                    handleCollectionRequest(response);
                }
                else if (response instanceof IServerBackRequest && ClientInfo.isAuthorized()){
                    handleServerBackRequest((IServerBackRequest) response);
                }
            }
            catch (ConnectionException exception){
                return;
            }
        }
    }

    private void handleServerBackRequest(IServerBackRequest request){
        TableViewHandler tableViewHandler = MainFormController.getMainFormController().getTableViewHandler();
        if (request instanceof AddModelServerRequest){
            tableViewHandler.addElement(request.getData());
        }
        else if (request instanceof UpdateModelServerRequest){
            tableViewHandler.updateElement(request.getData());
        }
        else if(request instanceof RemoveModelServerRequest){
            tableViewHandler.removeElement(request.getData());
        }
    }

    /**
     * Process auth request.
     * @param response
     */
    private void handleAuthorizationRequest(IRequest response){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            pipedOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            printer.print("Can not write command request to the piped stream!");
        }
    }

    /**
     * Process command request.
     * @param response
     */
    private void handleCommandRequest(IRequest response){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            pipedOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            printer.print("Can not write command request to the piped stream!");
        }
    }

    /**
     * Process validation request.
     * @param response
     */
    private void handleValidationRequest(IRequest response){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            pipedOutputStream.write(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            printer.print("Can not write validation request to the piped stream!");
        }
    }

    /**
     * Process message request.
     * @param response
     */
    private void handleMessageRequest(IRequest response){
        printer.print((String)response.getData());
    }

    private void handleCollectionRequest(IRequest request){
        CollectionRequest collectionRequest = (CollectionRequest) request;
        MainFormController.updateCollectionList(collectionRequest.getData());
    }

    public static Condition getCondition(){
        return condition;
    }

    public static ReentrantLock getLocker(){
        return locker;
    }
}
