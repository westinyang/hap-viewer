package org.ohosdev.hapviewer.ctrl;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.ohosdev.hapviewer.App;
import org.ohosdev.hapviewer.common.Const;
import org.ohosdev.hapviewer.util.CommandUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * About controller
 * @author westinyang
 */
public class About implements Initializable {

    public Pane rootPane;
    public Pane backPane;
    public Label appName, appVersion, openSourceRepo, openSourceCommunity, techExchanges, author;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backPane.setStyle("-fx-background-color: #f0f0f0;");
        rootPane.setStyle("-fx-background-color: #ffffff");

        appName.setText(Const.APP_NAME);
        appVersion.setText(Const.APP_VERSION);
        openSourceRepo.setText("https://gitee.com/ohos-dev/hap-viewer");
        openSourceRepo.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                CommandUtil.openInDefaultBrowser("https://gitee.com/ohos-dev/hap-viewer");
            }
            event.consume();
        });
        openSourceCommunity.setText("https://gitee.com/ohos-dev");
        openSourceCommunity.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                CommandUtil.openInDefaultBrowser("https://gitee.com/ohos-dev");
            }
            event.consume();
        });
        techExchanges.setText("752399947");
        techExchanges.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                CommandUtil.openInDefaultBrowser("https://b.r.sn.cn/gXjhXz");
            }
            event.consume();
        });
        author.setText("westinyang");
        author.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                CommandUtil.openInDefaultBrowser("https://docs.qq.com/doc/DQVlkcnlQUEFiQ3Rl");
            }
            event.consume();
        });
    }

    public void back(MouseEvent mouseEvent) {
        App.stage.setScene(App.scene);
    }
}
