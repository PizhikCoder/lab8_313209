package client.backend.core;

import client.backend.commands.Command;
import client.backend.core.Exceptions.UnknownCommandException;
import shared.interfaces.IPrinter;

import java.io.*;

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
            printer.print("Something went wrong with script file...");
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
                invoker.invokeCommand(command, commandLineArray[1]);
            }
        }
        catch (UnknownCommandException unknownCommandException){
            printer.print(unknownCommandException.getMessage());
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
            printer.print("Can not read from file!");
            return "";
        }
    }

    
    public boolean isWorking() {
        return isWorking;
    }
}
