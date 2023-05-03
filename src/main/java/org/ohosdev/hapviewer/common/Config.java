package org.ohosdev.hapviewer.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置
 * @author @westinyang
 */
public class Config {

    private static final String configFilePath;
    private static Map<String, Object> config = new HashMap<>();

    public static final String KEY_app_menu_fileOpenPath = "app.menu.fileOpenPath";
    public static final String KEY_app_main_iconSavePath = "app.main.iconSavePath";

    static {
        configFilePath = System.getProperty("user.home") + File.separator + ".HapViewer" + File.separator + "config.json";
    }

    public static String get(String key) {
        return config.get(key) == null ? null : StrUtil.toString(config.get(key));
    }

    public static void set(String key, String val) {
        config.put(key, val);
        write();
    }

    public static void del(String key) {
        config.remove(key);
        write();
    }

    public static void read() {
        if (!FileUtil.exist(configFilePath)){
            FileUtil.writeUtf8String("{}", configFilePath);
        }
        String configContent = FileUtil.readUtf8String(configFilePath);
        config = JSONUtil.toBean(configContent, HashMap.class);
    }

    private static void write() {
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(config), configFilePath);
    }

}
