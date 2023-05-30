package client.UI.resourcebundles.runtimeoutputsbundles;

import java.util.ListResourceBundle;

public class RuntimeOutputRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] content = {
                {"unknownCommandExceptionOutput", "Неизвестная команда: %s"},
                {"fileListenerCanNotReadFromFile", "Не удалось прочитать из файла!"},
                {"fileListenerProblemWithScriptFile", "Проблема с файлом скрипта..."},
                {"connectionUnknownHostName", "Неизвестное имя хоста!"},
                {"connectionUnknownClassReceived", "Получен неизвестный класс!"},
                {"connectionCouldNotBeEstablished", "Не удалось установить соединение!"},
                {"connectionReconnecting", "Переподключение..."},
                {"canNotCloseChannelWithServer", "Не удалось закрыть канал с сервером!"},
                {"connectionReconnected", "Соединение восстановлено!"},
                {"connectionReconnectionFailed", "Не удалось восстановить соединение!"},
                {"tcpSenderCanNOtSendRequest", "Не удалось отправить запрос!"},
                {"canNonInitComponent", "Не удалось инициализировать программный компонент!"},
                {"anyFieldsDoesNotValid", "Некорректные значения в полях!"},
                {"modelWasNotSelectedInTable", "Выберите модель в таблице."},
                {"columnWasNotSelected", "Выберите столбец!"},
                {"signWasNotSelected", "Выберите знак!"},
                {"valueWasNotEntered", "Введите значение!"}
        };
        return content;
    }
}
