package client.backend.commands;

import shared.core.exceptions.*;

public abstract class Command {
    /**
     * The method triggers the execution of the command.
     * @param args Arguments for the command.
     * @return Summary string of command execution.
     * @throws RecursionException
     * @throws FileAccessException
     * @throws CommandParamsException
     * @throws FileDoesNotExistException
     */
    public abstract boolean execute(String ... args) throws CommandParamsException;
}
