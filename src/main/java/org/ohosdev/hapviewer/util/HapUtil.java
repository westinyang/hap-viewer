package org.ohosdev.hapviewer.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import javafx.scene.image.Image;
import org.ohosdev.hapviewer.model.HapInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * HapUtil
 * @author westinyang
 */
public class HapUtil {

    /**
     * 解析hap
     * @param hapFilePath hap文件路径
     * @return HapInfo
     */
    public static HapInfo parse(String hapFilePath) throws IOException {
        HapInfo hapInfo = new HapInfo();
        ZipFile zipFile = null;
        try {
            hapInfo.hapFilePath = hapFilePath;
            zipFile = new ZipFile(hapFilePath);
            // 读取module.json
            JSONObject module = getEntryToJsonObject(zipFile, "module.json");
            // 读取pack.info
            // JSONObject pack = getEntryJsonObject(zipFile, "pack.info");

            // app.
            hapInfo.packageName = module.getByPath("app.bundleName", String.class);
            hapInfo.versionName = module.getByPath("app.versionName", String.class);
            hapInfo.versionCode = module.getByPath("app.versionCode", String.class);
            hapInfo.vendor = module.getByPath("app.vendor", String.class);
            hapInfo.minAPIVersion = module.getByPath("app.minAPIVersion", String.class);
            hapInfo.targetAPIVersion = module.getByPath("app.targetAPIVersion", String.class);
            hapInfo.apiReleaseType = module.getByPath("app.apiReleaseType", String.class);

            // module.
            hapInfo.mainElement = module.getByPath("module.mainElement", String.class);

            // 解析图标
            JSONArray moduleAbilities = module.getByPath("module.abilities", JSONArray.class);
            JSONObject targetAbility = null;
            try {
                targetAbility = (JSONObject) moduleAbilities.get(0);
            } catch (Exception ignore) {}
            for (Object item : moduleAbilities) {
                JSONObject ability = (JSONObject) item;
                if (hapInfo.mainElement.equals(ability.get("name", String.class))) {
                    targetAbility = ability;
                    break;
                }
            }
            if (targetAbility != null) {
                String iconName = targetAbility.get("icon", String.class).split(":")[1];
                String iconPath = String.format("resources/base/media/%s.png", iconName);
                hapInfo.iconPath = iconPath;
                hapInfo.iconBytes = getEntryToBytes(zipFile, iconPath);
                hapInfo.icon = getEntryToImage(zipFile, iconPath);
            }

            // 解析名称
            byte[] resourcesIndexBytes = getEntryToBytes(zipFile, "resources.index");
            String resourcesIndexHex = HexUtil.encodeHexStr(resourcesIndexBytes).toUpperCase();
            String appNameHex = ReUtil.get("(00.{2}0000000900000003000001.{2}00)(.*?)(00.{2}00)", resourcesIndexHex, 2);
            hapInfo.appName = HexUtil.decodeHexStr(appNameHex);

            // 技术探测，暂时先简单判断时间，后续抽离到配置文件
            Set<String> techList = new HashSet<>();
            ZipUtil.read(zipFile, (zipEntry) -> {
                // System.out.println(zipEntry.getName());
                if (ReUtil.contains("libs\\/arm.*\\/libcocos.so", zipEntry.getName())
                        || ReUtil.contains("ets\\/workers\\/CocosWorker.abc", zipEntry.getName())
                ) {
                    techList.add("Cocos");
                }
            });
            hapInfo.techList = techList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("HAP file parse failed");
        } finally {
            IoUtil.close(zipFile);
        }

        return hapInfo;
    }

    /**
     * 读取文件为JsonObject
     * @param zipFile zip文件
     * @param entryName 条目名称
     * @return
     */
    public static JSONObject getEntryToJsonObject(ZipFile zipFile, String entryName) {
        ZipEntry entry = zipFile.getEntry(entryName);
        InputStream is = null;
        try {
            is = zipFile.getInputStream(entry);
            var content = IoUtil.readUtf8(is);
            return JSONUtil.parseObj(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtil.close(is);
        }
    }

    /**
     * 读取文件为图像
     * @param zipFile zip文件
     * @param entryName 条目名称
     * @return
     */
    public static Image getEntryToImage(ZipFile zipFile, String entryName) {
        ZipEntry entry = zipFile.getEntry(entryName);
        InputStream is = null;
        try {
            is = zipFile.getInputStream(entry);
            return new Image(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtil.close(is);
        }
    }

    /**
     * 读取文件为字节数组
     * @param zipFile zip文件
     * @param entryName 条目名称
     * @return
     */
    public static byte[] getEntryToBytes(ZipFile zipFile, String entryName) {
        ZipEntry entry = zipFile.getEntry(entryName);
        InputStream is = null;
        try {
            is = zipFile.getInputStream(entry);
            return IoUtil.readBytes(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtil.close(is);
        }
    }

}
