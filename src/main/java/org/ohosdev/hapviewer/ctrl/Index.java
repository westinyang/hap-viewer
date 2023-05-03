package org.ohosdev.hapviewer.ctrl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.ohosdev.hapviewer.App;
import org.ohosdev.hapviewer.common.Config;
import org.ohosdev.hapviewer.model.HapInfo;
import org.ohosdev.hapviewer.util.CommandUtil;
import org.ohosdev.hapviewer.util.FxUtil;
import org.ohosdev.hapviewer.util.HapUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;

/**
 * Index controller
 * @author westinyang
 */
public class Index implements Initializable {
    public Pane rootPane;
    public MenuBar menuBar;
    public HBox iconWrapper;
    public ImageView icon;
    public Label appName, packageName, versionName, versionCode, targetAPIVersion, techDesc;

    public static HapInfo currentHapInfo = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 界面样式
        menuBar.setUseSystemMenuBar(false);
        menuBar.setStyle("-fx-background-color: #f0f0f0;");
        rootPane.setStyle("-fx-background-color: #ffffff");

        // 菜单绑定
        ContextMenu contextMenu = new ContextMenu();
        // 菜单项
        MenuItem redBg = new MenuItem("保存（同级目录）");
        redBg.setOnAction(event -> {
            saveIcon(true);
        });
        // 菜单项
        MenuItem blueBg = new MenuItem("另存为...");
        blueBg.setOnAction(event -> {
            saveIcon(false);
        });
        contextMenu.getItems().addAll(redBg, blueBg);
        icon.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                contextMenu.hide();
            } else if (event.getButton().name().equals(MouseButton.SECONDARY.name())) {
                // contextMenu.show(icon, Side.BOTTOM, 0, 0);
                contextMenu.show(icon, event.getScreenX(), event.getScreenY());
            }
            event.consume();
        });

        // 启动参数判断
        String args1 = App.STARTUP_PARAM_1;
        if (StrUtil.isNotEmpty(args1) && FileUtil.exist(args1) && "hap".equals(FileUtil.extName(args1))) {
            parseHapAndShowInfo(args1);
        }
    }

    public void saveIcon(boolean isDefaultPath) {
        HapInfo hapInfo = currentHapInfo;
        // 检查hap路径
        if (hapInfo == null || StrUtil.isEmpty(hapInfo.hapFilePath)) {
            FxUtil.notification("请先打开一个hap文件");
            return;
        }
        // 判断默认路径还是另存为
        String outDir = FileUtil.file(hapInfo.hapFilePath).getParent();
        String outName = FileNameUtil.getPrefix(hapInfo.hapFilePath) + "." + FileNameUtil.getSuffix(hapInfo.iconPath);
        if (!isDefaultPath) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            // 设置初始目录
            var initPath = Config.get(Config.KEY_app_main_iconSavePath);
            if (StrUtil.isNotEmpty(initPath) && FileUtil.exist(initPath)) {
                directoryChooser.setInitialDirectory(FileUtil.file(initPath));
            }
            File file = directoryChooser.showDialog(rootPane.getScene().getWindow());
            if (file == null) {
                return;
            }
            outDir = file.getPath();
            // 记录初始目录
            Config.set(Config.KEY_app_main_iconSavePath, file.getAbsolutePath());
        }
        String outPath = outDir + FileUtil.FILE_SEPARATOR + outName;
        // 保存图标
        try {
            FileUtil.writeBytes(hapInfo.iconBytes, outPath);
            FxUtil.notification("保存完毕");
        } catch (Exception e) {
            FxUtil.notification("保存失败：" + e.getMessage());
        }
    }

    public void fileOpenClick(ActionEvent actionEvent) {
        Window window = rootPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        // 文件类型过滤器
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("hap files (*.hap)", "*.hap");
        fileChooser.getExtensionFilters().add(filter);
        // 设置初始目录
        var initPath = Config.get(Config.KEY_app_menu_fileOpenPath);
        if (StrUtil.isNotEmpty(initPath) && FileUtil.exist(initPath)) {
            fileChooser.setInitialDirectory(FileUtil.file(initPath));
        }
        // ...
        File file = fileChooser.showOpenDialog(window);
        String fileName = file == null ? "" : file.getName();
        String fileAbsolutePath = file == null ? "" : file.getAbsolutePath();

        if (file == null) {
            return;
        }
        // 记录初始目录
        Config.set(Config.KEY_app_menu_fileOpenPath, file.getParent());

        // 解析hap并显示信息
        parseHapAndShowInfo(fileAbsolutePath);
    }

    public void fileSettingsClick(ActionEvent actionEvent) {
        FxUtil.notification("待开发...");
    }

    public void fileExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void toolInstall(ActionEvent actionEvent) {
        CommandUtil.installHap(currentHapInfo, true);
    }

    public void toolUninstall(ActionEvent actionEvent) {
        CommandUtil.uninstallHap(currentHapInfo);
    }

    public void toolUninstallAndReinstall(ActionEvent actionEvent) {
        CommandUtil.installHap(currentHapInfo, false);
    }

    public void toolUnpack(ActionEvent actionEvent) {
        HapInfo hapInfo = currentHapInfo;
        // 检查hap路径
        if (hapInfo == null || StrUtil.isEmpty(hapInfo.hapFilePath)) {
            FxUtil.notification("请先打开一个hap文件");
            return;
        }
        // 解压（同级目录）
        try {
            String out =  FileUtil.file(hapInfo.hapFilePath).getParent() + FileUtil.FILE_SEPARATOR + FileNameUtil.getPrefix(hapInfo.hapFilePath);
            ZipUtil.unzip(hapInfo.hapFilePath, out);
            FxUtil.notification("解压完成：" + out);
        } catch (Exception e) {
            e.printStackTrace();
            FxUtil.notification(e.getMessage());
        }
    }

    public void toolResign(ActionEvent actionEvent) {
        FxUtil.notification("待开发...");
    }

    public void helpAbout(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("about.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        App.stage.setScene(scene);
    }

    /**
     * 解析hap并显示信息
     * @param hapFilePath
     */
    private void parseHapAndShowInfo(String hapFilePath) {
        // 解析hap
        HapInfo hapInfo;
        try {
            hapInfo = HapUtil.parse(hapFilePath);
            currentHapInfo = hapInfo;
            icon.setImage(hapInfo.icon);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            Platform.runLater(() -> FxUtil.alert(Alert.AlertType.ERROR, "hap文件解析失败（目前仅支持API9+编译的安装包）"));
            return;
        }

        // 显示信息
        this.appName.setText(hapInfo.appName);
        this.packageName.setText(hapInfo.packageName);
        this.versionName.setText(hapInfo.versionName);
        this.versionCode.setText(hapInfo.versionCode);
        this.targetAPIVersion.setText(String.format("API%s (%s)", hapInfo.targetAPIVersion, hapInfo.apiReleaseType));
        this.techDesc.setText(hapInfo.getTechDesc());
    }

    public void rootPaneOnDragOver(DragEvent event) {
        // 拖拽到rooPane & 有文件 & 只有一个文件 & 文件必须是hap后缀
        if (event.getGestureSource() != rootPane
                && event.getDragboard().hasFiles()
                && event.getDragboard().getFiles().size() == 1
                && FileUtil.extName(event.getDragboard().getFiles().get(0).getName()).equals("hap")) {

            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    public void rootPaneOnDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            success = true;
            // 获取拖拽的文件列表
            List<File> fileList = dragboard.getFiles();
            // 获取第一个文件
            File file = fileList.get(0);
            // 解析hap并显示信息
            parseHapAndShowInfo(file.getAbsolutePath());
        }
        event.setDropCompleted(success);
        event.consume();
    }

    public void copyOnClick(ActionEvent event) {
        var b = (Button) event.getSource();
        var valueId = StrUtil.toString(b.getUserData());
        var nameId = valueId + "Label";
        var value = ((Label) b.getScene().lookup(valueId)).getText();
        var name = ((Label) b.getScene().lookup(nameId)).getText().replace("：", "");

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent cc = new ClipboardContent();
        cc.put(DataFormat.PLAIN_TEXT, value);
        clipboard.setContent(cc);

        FxUtil.notification(String.format("已复制 %s", name));
    }
}