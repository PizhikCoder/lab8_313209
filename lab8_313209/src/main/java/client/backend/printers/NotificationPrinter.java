package client.backend.printers;

import javafx.application.Platform;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;
import shared.interfaces.IPrinter;

public class NotificationPrinter implements IPrinter {

    @Override
    public void print(String data) {
        Platform.runLater(()->Notifications.create().text(data).position(Pos.TOP_CENTER).show());
    }
}
