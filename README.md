# HapViewer

<img src="src/main/resources/icon/icon.png" width="128px" />

## 项目介绍

一个跨平台的hap查看器，方便开发者在电脑上预览hap的信息，并提供一键安装到设备等功能

目前支持解析的应用

- 应用平台：OpenHarmony、HarmonyOS
- 编译目标：API9+
- 应用模型：Stage（FA模型暂不考虑）

开源仓库

- [Gitee](https://gitee.com/westinyang/hap-viewer)
- [Github](https://github.com/westinyang/hap-viewer)

> 技术咨询来这里 [联系方式](https://gitee.com/westinyang/openharmony-creation/blob/master/README.md)

### 系列项目

- **电脑版（跨平台）**：[westinyang/hap-viewer](https://gitee.com/westinyang/hap-viewer)
- 手机版（Android）：[westinyang/hap-viewer-android](https://gitee.com/westinyang/hap-viewer-android)

### 衍生项目

- [hapv-cli](https://gitee.com/ericple/hapv-cli) `命令行版的hap查看器，目前由@ericple维护`

## 下载安装

- https://gitee.com/westinyang/hap-viewer/releases

## 使用说明

- 安装和卸载功能需要自己配置好hdc到环境变量
- 由于目前hdc的bug，hdc list targets不显示Android设备，但安装时候会监测到Android设备，如果同时连接Android和OpenHarmony设备，使用hdc安装命令会提示需要指定一个（我觉得这是个bug），那目前软件本身还没有做设备列表的读取和选择，只支持连接一个设备进行操作

## 开发技术

> GraalVM让Java再次变得强大，使用`NativeImage`把程序编译为目标平台的可执行文件，脱离jvm直接运行，启动速度快，内存负载低。  
> 关于GraalVM技术的最佳实践和教程，请参考我的另一个开源项目：[westinyang/java-graalvm-start](https://gitee.com/westinyang/java-graalvm-start)

- GraalVM CE 22.3.0 (Java 17) Native Image
- JavaFX 21-ea+5
- ControlsFX 11.1.2
- gluonfx-maven-plugin

## 功能列表

- [x] 打开应用（选择、拖拽、默认打开方式）
- [x] 解析应用（基本信息、逆向解析resources.index读取应用名称）
- [x] 技术探测（原生开发、Cocos、Flutter、Qt）
- [x] 实用工具（安装、重装、卸载、解压）
- [x] 解析 API9 Stage 模型的安装包
- [ ] ~~解析 API9 FA 模型 (ArkTS/JS) 的安装包~~

## 兼容测试

- Windows 7 `未测试，理论兼容`
- Windows 10/11 `已测试`
- macOS 11.x Big Sur (Intel) `已测试`
- Ubuntu 20.04 `已测试`

## 截图预览

![](screenshot/all.png)

## 视频演示

- [OpenHarmony首个跨平台的hap查看器，将Open进行到底，为开源鸿蒙生态助力](https://www.bilibili.com/video/BV1HX4y127ub)
- [开源鸿蒙hap查看器，新增探测Flutter、Qt技术，安卓版新主题](https://www.bilibili.com/video/BV1cg4y197mc)
- [开源鸿蒙hap查看器，里程碑式新算法，API9~10、系统应用也支持](https://www.bilibili.com/video/BV1Wb4y1T7VL)

## 许可声明

- 本项目是以Apache2.0许可开源，如需衍生或商用请注明出处
- 软件图标出处：https://www.flaticon.com/free-icon/apk_9704667
