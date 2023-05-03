package org.ohosdev.hapviewer.util;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

/**
 * JavaFx组件封装工具
 * @author westinyang
 */
public class FxUtil {
    static final String DEFAULT_TITLE = "提示";

    public static void alert(Alert.AlertType alertType, String content) {
        alert(alertType, DEFAULT_TITLE, content);
    }

    public static void alert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(content);
        alert.show();
    }

    public static void notification(String content) {
        notification(DEFAULT_TITLE, content);
    }

    public static void notification(String title, String content) {
        notification(title, content, Duration.seconds(3f));
    }

    public static void notification(String title, String content, Duration hideAfter) {
        Notifications n = Notifications.create();
        n.position(Pos.TOP_CENTER);
        n.hideAfter(hideAfter);
        n.title(title);
        n.text(content);
        n.show();
    }

    public void showNotificationPane(Stage stage, String text) {
        Scene scene = stage.getScene();
        Parent pane = scene.getRoot();
        if (!(pane instanceof NotificationPane)){
            NotificationPane notificationPane = new NotificationPane(pane);
            scene = new Scene(notificationPane, scene.getWidth(), scene.getHeight());
            stage.setScene(scene);
            notificationPane.setText(text);
            notificationPane.show();
        } else {
            ((NotificationPane)pane).show();
        }
    }

}
