# StatusBarHelper

一个状态栏适配工具类，仅支持 Android 5.0 (API 21) 以上

PS：不想支持4.4

发现代码问题请务必 Issues 或 Pull Requests ！

#

## 用法

### 设置沉浸（用于普通情况）
```java
StatusBarHelper.setImmersiveWindow(window, boolean);
```

### 设置状态栏黑色图标及文字（支持 MIUI V6 以上，Flyme 4 以上，原生 Android 6.0 以上）
```java
StatusBarHelper.setStatusBarDarkMode(window, boolean);
```

### 设置沉浸（针对 DrawerLayout）
```java
 StatusBarHelper.setImmersiveWindowForDrawer(context, drawer, toolbar);
```

### 更多用法请查看源码  [StatusBarHelper.java](https://github.com/Omico/StatusBarHelper/blob/master/yuwen-statusbar-helper/src/main/java/com/yuwen/support/util/statusbar/StatusBarHelper.java)

#

## 开源协议
```
Copyright 2016 Omico

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
