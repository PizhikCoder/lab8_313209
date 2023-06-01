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
                {"countGreaterThanFrontManNotExecuted", "Введенный рост в неверном формате!(Ожидалось: 0<float<=240)"},
                {"groupCountingByCoordinatesExecuted", "\nГруппа координат: %s\nКоличество элементов: %s\n"},
                {"collectionIsEmpty", "Коллекция пустая!"},
                {"addIfMinCommandIdInWrongFormat", "Введен неверный формат ID!"},
                {"addIfMinCommandIdIsNotMin", "Введенный ID не является минимальным!"},
                {"infoCommandExecuted", "Информация о коллекции:" +
                        "\n---Тип: Музыкальная группа" +
                        "\n---Количество элементов: %s"},
                {"idDoesNotExist", "Введенный идентификатор не существует!"}
        };
        return content;
    }
}
