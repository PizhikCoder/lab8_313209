package client.UI.resourcebundles.commandsanswersbundles;

import java.util.ListResourceBundle;

public class CommandsAnswersRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"addCommandExecuted", "Объект был создан!"},
                {"addCommandNotExecuted", "Объект не был создан!!"},
                {"addIfMinCommandExecuted", "Объект был создан!"},
                {"addIfMinCommandNotExecuted", "Объект не был создан!!"},
                {"clearCommandExecuted", "Модели были очищены!"},
                {"executeScriptCommandExecuted", "Скрипт выполнен!"},
                {"executeScriptRecursionDetected", "Рекурсия зафиксирована! Выполнение скрипты прервано!"},
                {"executeScriptCanNotAccessToFile", "Не удается получить доступ к файлу: %s!"},
                {"executeScriptCanNotFoundFile", "Файл: %s не существует!"},
                {"removeByIdCommandExecuted", "Объект был удален!"},
                {"removeByIdCommandNotExecuted", "Объект не был удален!"},
                {"signInCommandExecuted", "Вы были авторизованы!"},
                {"signInCommandNotExecuted", "Неверный логин или пароль!"},
                {"signUpCommandExecuted", "Аккаунт был зарегистрирован!"},
                {"signUpCommandNotExecuted", "Аккаунт не был зарегистрирован!"},
                {"updateCommandExecuted", "Объект был обновлен!"},
                {"updateCommandNotExecuted", "Выбранный объект принадлежит другому пользователю!"},
                {"countGreaterThanFrontManExecuted", "Музыкальных групп с солистом, выше чем %s: %s"},
                {"countGreaterThanFrontManNotExecuted", "Введенный рост в неверном формате!(Ожидалось: float, не null)"},
                {"groupCountingVyCoordinatesExecuted", "\nГруппа координат: %s\nКоличество элементов: %s\n"},
                {"collectionIsEmpty", "Коллекция пустая!"}
        };
        return content;
    }
}
