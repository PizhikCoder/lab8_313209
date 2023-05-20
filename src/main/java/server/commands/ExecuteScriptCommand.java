package server.commands;

import server.core.Invoker;

public class ExecuteScriptCommand extends Command{
    public ExecuteScriptCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public String getCommandInfo() {
        return "Command \"execute_script <filename>\": Read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.";
    }
}
