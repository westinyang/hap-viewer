package org.ohosdev.hapviewer.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import javafx.application.Platform;
import org.ohosdev.hapviewer.model.HapInfo;

import java.util.regex.Pattern;

/**
 * 执行命令工具
 * @author westinyang
 */
public class CommandUtil {

    /**
     * 安装hap
     * @param hapInfo 安装包信息
     * @param override 是否覆盖安装
     */
    public static void installHap(HapInfo hapInfo, boolean override) {
        // 检查hap路径
        if (hapInfo == null || StrUtil.isEmpty(hapInfo.hapFilePath)) {
            FxUtil.notification("请先打开一个hap文件");
            return;
        }

        // 检查环境
        if (checkEnv()) {
            return;
        }

        try {
            ThreadUtil.execAsync(() -> {
                // 卸载（卸载重装时执行）
                if (!override) {
                    // Platform.runLater(() -> FxUtil.notification("正在卸载..."));
                    Process uninstallProcess = RuntimeUtil.exec("hdc", "app", "uninstall", hapInfo.packageName);
                    try {
                        uninstallProcess.waitFor();
                    } catch (InterruptedException ignored) {
                    }
                }

                // 校验安装目录是否包含中文，hdc命令目前有个bug，不支持包含中文目录，如果存在中文目录，把安装包复制到临时目录再安装
                String tmpHapPath = null;
                String finalHapPath = hapInfo.hapFilePath;
                if (Validator.hasChinese(hapInfo.hapFilePath)) {
                    tmpHapPath = FileUtil.getTmpDirPath() + FileUtil.FILE_SEPARATOR + "haps" + FileUtil.FILE_SEPARATOR + String.format("%d.hap", DateUtil.current());
                    finalHapPath = tmpHapPath;
                    FileUtil.copy(hapInfo.hapFilePath, tmpHapPath, true);
                }

                // 安装
                // Platform.runLater(() -> FxUtil.notification("正在安装..."));
                Process installProcess = RuntimeUtil.exec("hdc", "app", "install", "-r", finalHapPath);
                try {
                    installProcess.waitFor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                var installResult = RuntimeUtil.getResult(installProcess);
                if (installResult.contains("msg:install bundle successfully.")) {
                    Platform.runLater(() -> FxUtil.notification(override ? "安装完成" : "卸载并安装完成"));
                } else if (installResult.contains("Specify one target")) {
                    Platform.runLater(() -> FxUtil.notification("暂不支持多设备连接的情况下安装，请检查"));
                } else {
                    Platform.runLater(() -> FxUtil.notification(installResult));
                }

                // 清理临时文件
                FileUtil.del(tmpHapPath);
            });
        } catch (Exception e) {
            e.printStackTrace();
            FxUtil.notification(e.getMessage());
        }
    }

    public static void uninstallHap(HapInfo hapInfo) {
        // 检查hap路径
        if (hapInfo == null || StrUtil.isEmpty(hapInfo.hapFilePath)) {
            FxUtil.notification("请先打开一个hap文件");
            return;
        }

        // 检查环境
        if (checkEnv()) {
            return;
        }

        try {
            // 卸载
            ThreadUtil.execAsync(() -> {
                // Platform.runLater(() -> FxUtil.notification("正在卸载..."));
                Process uninstallProcess = RuntimeUtil.exec("hdc", "app", "uninstall", hapInfo.packageName);
                try {
                    uninstallProcess.waitFor();
                } catch (InterruptedException ignored) {
                }
                 var uninstallResult = RuntimeUtil.getResult(uninstallProcess);
                 if (uninstallResult.contains("msg:uninstall bundle successfully.")) {
                     Platform.runLater(() -> FxUtil.notification("卸载完成"));
                 } else {
                     Platform.runLater(() -> FxUtil.notification("未安装此应用"));
                 }
            });
        } catch (Exception e) {
            e.printStackTrace();
            FxUtil.notification(e.getMessage());
        }
    }

    /**
     * 检查环境，hdc是否正确配置，oh设备是否连接
     * @return 环境是否正常
     */
    public static boolean checkEnv() {
        try {
            // 检测设备是否连接
            Process process = RuntimeUtil.exec("hdc", "list", "targets");
            process.waitFor();
            var result = RuntimeUtil.getResult(process);
            if (result.contains("[Empty]")) {
                FxUtil.notification("未连接OpenHarmony设备");
                return true;
            }

            return false;
        } catch (Exception e) {
            if (e.getMessage().contains("Cannot run program \"hdc\"")) {
                FxUtil.notification("请先配置hdc命令到环境变量");
            } else {
                e.printStackTrace();
                FxUtil.notification(e.getMessage());
            }
            return true;
        }
    }

    public static void openInDefaultBrowser(String url) {
        String osName = System.getProperty("os.name", "");
        if (Pattern.matches("Windows.*", osName)) {
            RuntimeUtil.exec("cmd", "/c", "start", url);
        } else if (Pattern.matches("Mac.*", osName)) {
            RuntimeUtil.exec("open", url);
        } else if (Pattern.matches("Linux.*", osName)) {
            RuntimeUtil.exec("x-www-browser", url);
        }
    }
}
