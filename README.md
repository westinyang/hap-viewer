# HapViewer

<img src="src/main/resources/icon/icon.png" width="128px" />

## 项目介绍

一个跨平台的hap查看器，方便开发者在电脑上预览hap的信息，并提供一键安装到设备等功能

目前支持解析的应用：

- 应用平台：OpenHarmony、HarmonyOS
- 编译目标：API9+
- 应用模型：Stage（FA模型的解析暂未兼容，后续实现...）

## 下载安装

- https://gitee.com/ohos-dev/hap-viewer/releases

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
- [x] 技术探测（原生开发、Cocos）
- [x] 实用工具（安装、重装、卸载、解压）
- [x] 解析 API9 Stage 模型的安装包
- [ ] 解析 API9 FA 模型 (ArkTS/JS) 的安装包

## 兼容测试

- Windows 7 `未测试，理论兼容`
- Windows 10/11 `已测试`
- macOS 11.x Big Sur (Intel) `已测试`
- Ubuntu 20.04 `已测试`

## 截图预览

![](screenshot/all.png)

## 视频演示

- https://www.bilibili.com/video/BV1HX4y127ub

## 许可声明

- 本项目是以Apache2.0许可开源，如果衍生或商用需保留原始版权和许可声明即可
- 软件图标出处：https://www.flaticon.com/free-icon/apk_9704667

## 赞助捐赠

- 我是一名独立开发者，HapViewer和一些开源项目都是我的兴趣投入
- 你的赞助捐赠将是 HapViewer 持续完善的驱动力！
- 赞助请私信，捐赠点下面的按钮
