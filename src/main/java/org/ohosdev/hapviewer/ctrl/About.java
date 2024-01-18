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
    public Label label1, label2, label3, label4, label5, label6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backPane.setStyle("-fx-background-color: #f0f0f0;");
        rootPane.setStyle("-fx-background-color: #ffffff");

        // 软件名称
        label1.setText(Const.APP_NAME);

        // 软件版本
        label2.setText(Const.APP_VERSION);

        // 软件作者
        label3.setText("westinyang");
        label3.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                CommandUtil.openInDefaultBrowser("https://kaihongpai.feishu.cn/wiki/CqWLwJRadibxztkrIWZcogWxnXd");
            }
            event.consume();
        });

        // 哔哩哔哩
        label4.setText("space.bilibili.com/74433635");
        label4.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                CommandUtil.openInDefaultBrowser("https://space.bilibili.com/74433635");
            }
            event.consume();
        });

        // 开源仓库
        label5.setText("gitee.com/ohos-dev/hap-viewer");
        label5.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                CommandUtil.openInDefaultBrowser("https://gitee.com/ohos-dev/hap-viewer");
            }
            event.consume();
        });

        // 知识星球
        label6.setText("了解/加入开鸿派知识星球");
        label6.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                CommandUtil.openInDefaultBrowser("https://kaihongpai.feishu.cn/wiki/Y4ajwv6uFi73AwkLxgbcdwctn2g");
            }
            event.consume();
        });
    }

    public void back(MouseEvent mouseEvent) {
        App.stage.setScene(App.scene);
    }
}
