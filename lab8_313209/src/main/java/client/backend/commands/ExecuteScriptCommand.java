package client.backend.commands;


import client.UI.resourcebundles.enums.CommandsAnswers;
import client.backend.core.FileListener;
import client.backend.core.Invoker;
import shared.core.exceptions.CommandParamsException;
import shared.interfaces.IPrinter;

import java.io.File;
import java.util.LinkedList;

public class ExecuteScriptCommand extends Command {
    private static boolean isRecursionDetected = false;
    private final int PATH_INDEX = 0;
    private final int EXPECTED_ARGUMENTS_COUNT = 1;
    private final IPrinter printer;
    private final LinkedList<String> pathChain;
    private LinkedList<FileListener> listenersContainer;

    public ExecuteScriptCommand(IPrinter printer) {
        this.printer = printer;
        pathChain = new LinkedList<>();
        listenersContainer = new LinkedList<>();
    }

    @Override
    public boolean execute(String... args) throws CommandParamsException {
        if (args.length < EXPECTED_ARGUMENTS_COUNT) {
            throw new CommandParamsException(args.length, EXPECTED_ARGUMENTS_COUNT);
        }
        String path = args[PATH_INDEX];
        if (!filePathCheck(path)) return false;

        FileListener listener = new FileListener(path, printer);

        if (recursionCheck(path)) {
            listenersContainer.add(listener);
            listener.start();
            pathChain.remove(path);
        } else {
            listenersContainer.forEach(FileListener::stop);
            isRecursionDetected = true;
            printer.print(CommandsAnswers.EXECUTE_SCRIPT_COMMAND_RECURSION_DETECTED.toString());
        }
        if (isRecursionDetected && listener == listenersContainer.getFirst()) {
            listenersContainer = new LinkedList<>();
            isRecursionDetected = false;
        } else if (listener == listenersContainer.getFirst()) {
            listenersContainer = new LinkedList<>();
            printer.print(CommandsAnswers.EXECUTE_SCRIPT_COMMAND_EXECUTED.toString());
            return true;
        }
        return false;
    }

    private boolean recursionCheck(String filePath) {
        if (pathChain.contains(filePath)) {
            return false;
        }
        pathChain.add(new File(filePath).getAbsolutePath());
        return true;
    }

    private boolean filePathCheck(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.canRead() && file.canWrite()) {
                return true;
            } else {
                printer.print(String.format(CommandsAnswers.EXECUTE_SCRIPT_COMMAND_CAN_NOT_ACCESS_TO_FILE.toString(), path));
                return false;
            }
        }
        printer.print(String.format(CommandsAnswers.EXECUTE_SCRIPT_CAN_NOT_FOUND_FILE.toString(), path));
        return false;
    }
}
