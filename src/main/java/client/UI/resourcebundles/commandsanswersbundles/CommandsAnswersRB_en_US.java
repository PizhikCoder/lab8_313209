package client.UI.resourcebundles.commandsanswersbundles;


import java.util.ListResourceBundle;

public class CommandsAnswersRB_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"addCommandExecuted", "Object was created!"},
                {"addCommandNotExecuted", "Object was not created!"},
                {"addIfMinCommandExecuted", "Object was created!"},
                {"addIfMinCommandNotExecuted", "Object was not created!"},
                {"clearCommandExecuted", "Models cleared!"},
                {"executeScriptCommandExecuted", "Script executed!"},
                {"executeScriptRecursionDetected", "Recursion detected! Script execution interrupted."},
                {"executeScriptCanNotAccessToFile", "Can not access to the file: %s!"},
                {"executeScriptCanNotFoundFile", "File: %s does not exist!"},
                {"removeByIdCommandExecuted", "Object removed!"},
                {"removeByIdCommandNotExecuted", "Object was not removed!"},
                {"signInCommandExecuted", "You were authorized!"},
                {"signInCommandNotExecuted", "Wrong login or password!"},
                {"signUpCommandExecuted", "Account was registered!"},
                {"signUpCommandNotExecuted", "Account was not registered!"},
                {"updateCommandExecuted", "Object was updated!"},
                {"updateCommandNotExecuted", "Selected object belongs to another user!"},
                {"countGreaterThanFrontManExecuted", "Music bands with front men, that higher than %s: %s"},
                {"countGreaterThanFrontManNotExecuted", "Entered height in wrong format!(Expected: float, not null)"},
                {"groupCountingByCoordinatesExecuted", "\nGroup of Coordinates: %s\nCount of elements: %s\n"},
                {"collectionIsEmpty", "Collection is empty!"}
        };
        return content;
    }
}
