package client.backend.core;

import client.UI.resourcebundles.enums.RuntimeOutputs;
import client.backend.commands.Command;
import client.backend.core.Exceptions.UnknownCommandException;
import shared.interfaces.IPrinter;

import java.io.*;
import java.util.concurrent.ExecutionException;

public class FileListener {

    private boolean isWorking;
    private String path;
    private BufferedReader reader;
    private IPrinter printer;

    public FileListener(String path, IPrinter printer){
        this.path = path;
        this.printer = printer;
    }

    
    public void start() {
        try{
            initReader();
            String line;
            while (isWorking){
                line = reader.readLine();
                if (line == null){
                    isWorking = false;
                    break;
                }
                if (line.isBlank()) continue;
                String[] commandLineArray = line.trim().split(" ");
                delegateCommandExecution(commandLineArray);
            }
        }
        catch (IOException ex){
            printer.print(RuntimeOutputs.FILE_LISTENER_PROBLEM_WITH_SCRIPT_FILE.toString());
        }
    }

    private void delegateCommandExecution(String[] commandLineArray) {
        Invoker invoker = Invoker.getInstance();
        CommandsManager commandsManager = new CommandsManager();
        try{
            Command command = commandsManager.findCommand(commandLineArray[0]).orElseThrow(()->new UnknownCommandException(commandLineArray[0]));
            if (commandLineArray.length == 1){
                invoker.invokeCommand(command);
            }
            else {
                invoker.invokeCommand(command, commandLineArray[1]).get();
            }
        }
        catch (UnknownCommandException unknownCommandException){
            printer.print(unknownCommandException.getMessage());
        }
        catch (ExecutionException | InterruptedException e) {
            System.err.println("Command thread interrupted while script executing!");
        }
    }

    private void initReader() throws FileNotFoundException {
        isWorking = true;
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(path));
        reader = new BufferedReader(streamReader);
    }

    
    public void stop() {
        isWorking = false;
    }

    
    public String nextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            printer.print(RuntimeOutputs.FILE_LISTENER_CAN_NOT_READ_FILE.toString());
            return "";
        }
    }

    
    public boolean isWorking() {
        return isWorking;
    }
}
