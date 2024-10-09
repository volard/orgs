package com.orgs.orgs.genericUI;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.ResourceBundle;

public class Notification {
    public static void showNotification(ResourceBundle bundle, NotificationType type, String message) {
        switch (type) {
            case ERROR -> Notifications.create()
                    .title(bundle.getString("notificationErrorTitle"))
                    .text(message)
                    .hideAfter(Duration.seconds(5))
                    .showError();
            case INFO -> Notifications.create()
                    .title(bundle.getString("notificationInfoTitle"))
                    .text(message)
                    .hideAfter(Duration.seconds(5))
                    .showInformation();
            case SUCCESS -> Notifications.create()
                    .title(bundle.getString("notificationSuccessTitle"))
                    .text(message)
                    .hideAfter(Duration.seconds(5))
                    .showConfirm();
        }
    }
}
