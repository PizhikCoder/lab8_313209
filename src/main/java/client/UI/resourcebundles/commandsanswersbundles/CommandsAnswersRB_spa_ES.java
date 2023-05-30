package client.UI.resourcebundles.commandsanswersbundles;


import java.util.ListResourceBundle;

public class CommandsAnswersRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"addCommandExecuted", "¡Objeto creado!"},
                {"addCommandNotExecuted", "¡El objeto no fue creado!"},
                {"addIfMinCommandExecuted", "¡Objeto creado!"},
                {"addIfMinCommandNotExecuted", "¡El objeto no fue creado!"},
                {"clearCommandExecuted", "¡Modelos eliminados!"},
                {"executeScriptCommandExecuted", "¡Script ejecutado!"},
                {"executeScriptRecursionDetected", "Se detectó recursión. La ejecución del script fue interrumpida."},
                {"executeScriptCanNotAccessToFile", "No se puede acceder al archivo: %s."},
                {"executeScriptCanNotFoundFile", "Archivo no encontrado: %s."},
                {"removeByIdCommandExecuted", "¡Objeto eliminado!"},
                {"removeByIdCommandNotExecuted", "¡El objeto no fue eliminado!"},
                {"signInCommandExecuted", "¡Has sido autorizado!"},
                {"signInCommandNotExecuted", "¡Usuario o contraseña incorrectos!"},
                {"signUpCommandExecuted", "¡Cuenta registrada!"},
                {"signUpCommandNotExecuted", "¡La cuenta no pudo ser registrada!"},
                {"updateCommandExecuted", "¡Objeto actualizado!"},
                {"updateCommandNotExecuted", "¡El objeto seleccionado pertenece a otro usuario!"},
                {"countGreaterThanFrontManExecuted", "Bandas de música con frontman más alto que %s: %s"},
                {"countGreaterThanFrontManNotExecuted", "Altura ingresada en formato incorrecto. (Se espera un número flotante)"},
                {"groupCountingByCoordinatesExecuted", "\nGrupo de coordenadas: %s\nCantidad de elementos: %s\n"},
                {"collectionIsEmpty", "La colección está vacía."},
                {"addIfMinCommandIdInWrongFormat", "¡ID ingresado en un formato incorrecto!"},
                {"addIfMinCommandIdIsNotMin", "¡El ID ingresado no es el mínimo!"},
                {"infoCommandExecuted", "Información de la colección:" +
                        "\n---Tipo: Banda de música" +
                        "\n---Cantidad de elementos: %s"},
                {"idDoesNotExist", "¡El ID ingresado no existe!"}
        };
        return content;
    }
}
