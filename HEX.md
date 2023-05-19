# 特征分析

```
00 2C 00 00 00 09 00 00 00 03 00 00 01 0D 00 ?????? 00 13 00 # 设备信息
00 24 00 00 00 09 00 00 00 03 00 00 01 05 00 ?????? 00 13 00 # F-OH
00 28 00 00 00 09 00 00 00 03 00 00 01 10 00 ?????? 00 0C 00 # 搜狗输入法
```

# 关键特征

```
00 00 00 09 00 00 00 03 00 00 01
```

# 伪正则

```regexp
00 ?? 00 00 00 09 00 00 00 03 00 00 01 ?? 00 ?????? 00 ?? 00
```

# 正则

```regexp
(00.{2}0000000900000003000001.{2}00)(.*?)(00.{2}00)
```

# Java代码片段
```java
byte[] resBytes = getEntryToBytes(zipFile, "resources.index");
String resHex = HexUtil.encodeHexStr(resBytes).toUpperCase();
String appNameHex = ReUtil.get("(00.{2}0000000900000003000001.{2}00)(.*?)(00.{2}00)", resHex, 2);
String appName = HexUtil.decodeHexStr(appNameHex);
```
