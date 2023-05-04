package org.ohosdev.hapviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.ohosdev.hapviewer.common.Config;
import org.ohosdev.hapviewer.common.Const;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 启动程序
 * @author westinyang
 */
public class App extends Application {
    public static String STARTUP_PARAM_1 = "";
    public static Stage stage;
    public static Scene scene;

    @Override
    public void init() throws Exception {
        try {
            var scaleX =  Screen.getScreens().get(0).getOutputScaleX();
            System.setProperty("glass.win.uiScale", String.valueOf(scaleX));
        } catch (Exception ignored) {
            System.setProperty("glass.win.uiScale", "1.0");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            App.stage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("index.fxml"));
            scene = new Scene(fxmlLoader.load());

            stage.setTitle(Const.APP_NAME);
            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon/icon.png"))));
            stage.centerOnScreen();
            stage.show();
            // 垂直居中
            /*Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            App.STARTUP_PARAM_1 = args[0];
        } catch (Exception ignored) {
        }
        Config.read();
        launch(args);
    }
}