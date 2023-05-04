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

## 开发技术

> GraalVM让Java再次变得强大，使用`NativeImage`把程序编译为目标平台的可执行文件，脱离jvm直接运行，启动速度快，内存负载低。  
> 关于GraalVM技术的最佳实践和教程，请参考我的另一个开源项目：[westinyang/java-graalvm-start](https://github.com/westinyang/java-graalvm-start)

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
- macOS Big Sur 11.x (Intel) `已测试`
- Ubuntu 20.04 `已测试`

## 截图预览

![](screenshot/all.png)

## 视频演示

x

## 许可声明

- 软件图标出处：https://www.flaticon.com/free-icon/apk_9704667

## 赞助捐赠

- 我是一名独立开发者，HapViewer和一些开源项目都是我的兴趣投入
- 你的赞助捐赠将是 HapViewer 持续完善的驱动力！
- 赞助请私信，捐赠点下面的按钮
