# Playlet 项目文档

## 目录

- [一、项目概述](#一项目概述)
  - [技术栈](#技术栈)
- [二、项目架构](#二项目架构)
  - [架构图](#架构图)
  - [架构分层说明](#架构分层说明)
- [三、编译环境准备](#三编译环境准备)
  - [3.1 基础环境](#31-基础环境)
  - [3.2 编译并运行](#32-编译并运行)
- [四、模块说明](#四模块说明)
  - [4.1 应用壳](#41-应用壳)
  - [4.2 基础库](#42-基础库)
  - [4.3 业务模块](#43-业务模块)
- [五、构建与分发](#五构建与分发)
  - [5.1 build-logic 构建体系](#51-build-logic-构建体系)
  - [5.2 Gradle 编译命令](#52-gradle-编译命令)
  - [5.3 编译产物目录](#53-编译产物目录)
  - [5.4 签名配置](#54-签名配置)
  - [5.5 分发配置](#55-分发配置)
- [六、开发指南](#六开发指南)
  - [6.1 组件化开发模式切换](#61-组件化开发模式切换)
  - [6.2 新 Module 创建模板](#62-新-module-创建模板)
  - [6.3 UI 开发规范](#63-ui-开发规范)
  - [6.4 网络请求使用流程](#64-网络请求使用流程)
  - [6.5 路由使用](#65-路由使用)
- [七、构建性能优化](#七构建性能优化)
- [八、相关文档索引](#八相关文档索引)

---

## 一、项目概述

**Playlet** 是一款 Android 移动端应用，采用 **MVVM + NRouter 路由** 的组件化开发架构。项目使用 **Kotlin** 作为主要开发语言，通过 **build-logic Gradle Convention Plugin** 共享构建逻辑，使用 **Version Catalog（libs.versions.toml）** 统一管理整个项目的依赖版本。

### 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 语言 | Kotlin | 2.1.10 |
| 构建工具 | AGP (Android Gradle Plugin) | 8.9.1 |
| 编译环境 | JDK | 21 |
| IDE | Android Studio | Quail 3 (2026.1.3) |
| 架构模式 | MVVM + NRouter | - |
| 注解处理 | KSP | 2.1.10-1.0.31 |
| 数据库 | Room | 2.6.1 |
| 网络请求 | Retrofit + OkHttp3 | 2.9.0 / 4.2.0 |
| 图片加载 | Glide | 4.11.0 |
| 视频播放 | ExoPlayer | 2.19.1 |
| 动画 | Lottie | 5.2.0 |
| 推送 | 友盟 + 厂商推送 | - |
| 崩溃监控 | Bugly | latest |
| 微信 SDK | wechat-sdk-android | 6.7.9 |
| 二维码 | ZXing | 3.5.0 |
| 图片选择 | PictureSelector | v3.11.2 |

---

## 二、项目架构

### 架构图

```
┌──────────────────────────────────────────────┐
│                  应用层 (app)                  │
│              整个 App 的壳工程                  │
│       MainActivity + ViewPager + 底部导航       │
├──────────────────────┬───────────────────────┤
│      业务层           │                       │
│  module_home (首页)   │                       │
│  module_theater       │                       │
│  (剧场)               │                       │
│  module_welfare       │                       │
│  (福利)               │                       │
│  module_me            │                       │
│  (我的)               │                       │
├──────────────────────┴───────────────────────┤
│              基础层                             │
│  lib_common (widget/util/base/view)           │
│  NRouter 路由 / LiveDataBus 事件总线            │
├──────────────────────────────────────────────┤
│                  数据层                        │
│  lib_coremodel                               │
│  (api / datasource / service / vm /          │
│   Room DB / entity / repository)             │
├──────────────────────────────────────────────┤
│                共享配置层                       │
│  build-logic (Convention Plugin)             │
│  Version Catalog (libs.versions.toml)        │
└──────────────────────────────────────────────┘
```

### 架构分层说明

项目采用经典的组件化分层架构，从下至上分为以下层次：

| 层次 | 模块 | 说明 |
|------|------|------|
| **共享配置层** | `build-logic` | Convention Plugin + Version Catalog，统一管理构建逻辑和依赖版本 |
| **数据层** | `lib_coremodel` | 核心数据层，包含 API 接口、DataSource、Service、ViewModel、Room 数据库、Entity 等 |
| **基础层** | `lib_common` | 公共库，包含 Base 类、UI 组件、自定义控件、工具类、事件总线等 |
| **业务层** | `module_home` / `module_theater` / `module_welfare` / `module_me` | 各业务功能模块，支持独立运行和集成编译 |
| **应用层** | `app` | 应用壳工程，组件化编译时依赖 lib_common，集成编译时引入所有业务模块 |

---

## 三、编译环境准备

### 3.1 基础环境

1. 确保已安装 **Android Studio Quail 3 (2026.1.3)** 和 **JDK 21**
2. 配置 `local.properties` 中的 Android SDK 路径
3. 确保网络可访问 Google Maven 和 Maven Central 仓库

### 3.2 编译并运行

1. 使用 Android Studio 打开项目根目录
2. 等待 Gradle 同步完成
3. 选择目标设备（模拟器或真机）
4. 选择 Build Variant（如 `devDebug`、`_testDebug`、`betaDebug`、`onlineRelease` 等）
5. 点击 Run 运行

或通过命令行编译：

```bash
# 编译测试环境 Debug 版本
./gradlew assemble_TestDebug

# 编译 Beta 环境 Debug 版本
./gradlew assembleBetaDebug

# 编译线上环境 Release 版本
./gradlew assembleOnlineRelease
```

---

## 四、模块说明

### 4.1 应用壳

| 模块 | 说明 |
|------|------|
| **app** | 应用壳工程，组件化编译时依赖 lib_common，集成编译时引入所有业务模块。包含 MainActivity（底部导航 + ViewPager 架构） |

### 4.2 基础库

| 模块 | 说明 |
|------|------|
| **lib_common** | 公共基础库，包含 Base 类（BaseActivity / BaseFragment）、自定义控件（banner、expandablelayout、videowebview 等）、工具类、事件、接口定义、Adapter 等。依赖 lib_coremodel |
| **lib_coremodel** | 核心数据层，负责 MVVM 中的 Model 和 ViewModel。包含：API 接口定义、DataSource、Service、ViewModel + Factory、Room 数据库（DAO / Entity / Repository）、网络请求实体（bean / request / response）等 |

### 4.3 业务模块

| 模块 | 说明 |
|------|------|
| **module_home** | 首页功能模块，包含首页 Fragment、Activity、Adapter、ViewHolder 等 |
| **module_theater** | 剧场模块，包含剧场浏览、播放等功能 |
| **module_welfare** | 福利模块，包含福利相关功能 |
| **module_me** | 个人中心模块，包含个人信息、设置等功能 |

> 所有业务模块均支持在 **application** 和 **library** 之间切换。组件化编译时可作为独立 App 运行，集成编译时作为模块被 app 壳工程依赖。每个业务模块内部遵循 `view`（activity / fragment / adapter / holder / dialog）分层结构。

---

## 五、构建与分发

### 5.1 build-logic 构建体系

项目使用 **Gradle Convention Plugin** 来统一管理构建逻辑，所有插件定义在 `build-logic/convention` 目录下。

| 插件 ID | 实现类 | 说明 |
|---------|--------|------|
| `com.victor.convention.base` | `AndroidBaseConventionPlugin` | Android 基础构建逻辑，配置 Kotlin、KSP、NRouter 等基础依赖 |
| `com.victor.convention.application` | `AndroidApplicationConventionPlugin` | Application 专用构建逻辑，含签名配置、BuildType、ProductFlavor |
| `com.victor.convention.module` | `AndroidModuleConventionPlugin` | 业务 Module 专用构建逻辑，支持 application/library 模式切换 |
| `com.victor.convention.lib` | `AndroidLibConventionPlugin` | 基础 Lib 专用构建逻辑 |
| `com.victor.convention.android.room` | `AndroidRoomConventionPlugin` | Room 数据库构建逻辑，配置 KSP + Room Schema |
| `com.victor.convention.open.source` | `OpenSourceConventionPlugin` | 第三方开源库依赖聚合，lib_coremodel 专用 |
| `com.victor.convention.lint` | `AndroidLintConventionPlugin` | Lint 代码检查配置 |
| `com.victor.convention.jvm.library` | `JvmLibraryConventionPlugin` | JVM 库构建逻辑 |

#### 依赖版本管理

所有依赖版本统一在 `gradle/libs.versions.toml` 中管理，包括：
- `[versions]` — 版本号定义
- `[libraries]` — 依赖库声明
- `[bundles]` — 依赖分组（如 victor、ktx、coroutines、lifecycle、retrofit、okhttp3、umeng 等）
- `[plugins]` — 插件声明

#### Product Flavors（多渠道配置）

| Flavor | 维度 | 环境说明 | UMENG_CHANNEL |
|--------|------|---------|---------------|
| `dev` | hok | 开发环境 | dev |
| `_test` | hok | 测试环境 | _test |
| `beta` | hok | UAT 环境 | uat |
| `online` | hok | 线上环境 | online |
| `huawei` | hok | 华为渠道 | huawei |
| `xiaomi` | hok | 小米渠道 | xiaomi |
| `oppo` | hok | OPPO 渠道 | oppo |
| `vivo` | hok | VIVO 渠道 | vivo |
| `tencent` | hok | 应用宝渠道 | tencent |

### 5.2 Gradle 编译命令

| 编译环境 | 渠道 | 编译类型 | 编译命令 |
|---------|------|---------|---------|
| 测试环境 | _test | debug | `./gradlew assemble_TestDebug` |
| UAT 环境 | beta | debug | `./gradlew assembleBetaDebug` |
| 线上环境 | online | release | `./gradlew assembleOnlineRelease` |
| 线上环境 | huawei/xiaomi/oppo/vivo/tencent | release | `./gradlew assemble{渠道}Release` |

### 5.3 编译产物目录

编译完成后，APK 输出在对应模块的 `build/outputs/apk/` 目录下。

APK 命名格式：`Playlet_{flavor}_{buildType}_v{versionName}_{versionCode}.apk`

示例：`Playlet_online_release_v1.0.0_1.apk`

### 5.4 签名配置

签名相关文件及配置位于 `build-logic/convention/src/main/java/com/victor/support/AppSignConfig.kt`：

| 配置项 | 值 |
|--------|-----|
| 签名文件 | `../docs/victor_keystore.jks` |
| Key Alias | `victor` |
| Key Password | `android` |
| Store Password | `android` |

> Debug 和 Release 构建类型均使用同一签名配置。

### 5.5 分发配置

APK 分发相关配置位于 `build-logic/convention/src/main/java/com/victor/support/UploadApkConfig.kt`：

| 配置项 | 说明 |
|--------|------|
| `FIR_API_TOKEN` | fir.im 平台 API Token |
| `PGYER_API_KEY` | 蒲公英平台 API Key |
| `UPLOAD_TO_FIR` | 是否上传到 fir.im |
| `UPLOAD_TO_PGYER` | 是否上传到蒲公英 |

辅助分发脚本：

| 文件 | 说明 |
|------|------|
| `docs/upToFir.py` | 上传 APK 到 fir.im 测试分发平台 |
| `docs/upToPgyer.py` | 上传 APK 到蒲公英测试分发平台 |

---

## 六、开发指南

### 6.1 组件化开发模式切换

项目通过 `AppBuildConfig.isModule` 开关控制组件化 / 集成化编译模式：

```kotlin
// build-logic/convention/src/main/java/com/victor/support/AppBuildConfig.kt
object AppBuildConfig {
    /**
     * isModule 是"集成开发模式"和"组件开发模式"的切换开关
     */
    const val isModule = false
    // ...
}
```

| 模式 | `isModule` 值 | 说明 |
|------|--------------|------|
| **集成编译** | `false` | app 壳工程依赖所有业务模块，完整编译 |
| **组件编译** | `true` | app 仅依赖 lib_common + lib_coremodel，业务模块可独立运行为 App |

当 `isModule = true` 时，业务模块使用 `src/main/module/AndroidManifest.xml` 作为 Manifest，可独立运行。

### 6.2 新 Module 创建模板

创建新业务模块时，`build.gradle.kts` 只需如下配置：

```kotlin
plugins {
    alias(libs.plugins.victor.android.module)
}
android {
    namespace = "com.victor.module.xxx"
}
```

然后在 `settings.gradle.kts` 中添加模块声明：

```kotlin
include(":module_xxx")
```

新模块需遵循以下目录结构：

```
module_xxx/
└── src/main/
    ├── java/com/victor/module/xxx/
    │   ├── view/
    │   │   ├── activity/    # Activity
    │   │   ├── fragment/    # Fragment
    │   │   ├── adapter/     # RecyclerView Adapter
    │   │   ├── holder/      # ViewHolder
    │   │   └── dialog/      # Dialog
    │   └── data/            # 模块内数据
    ├── module/
    │   └── AndroidManifest.xml  # 组件独立运行时的 Manifest
    └── res/                     # 资源文件
```

> 注意：资源名会自动添加模块名前缀（如 `home_`），避免资源名冲突。

### 6.3 UI 开发规范

1. 图片下载 **mipmap-xxhdpi** 尺寸
2. 使用 [TinyPNG](https://tinypng.com/) 压缩 PNG 后再添加到 mipmap-xxhdpi
3. 图片命名规范：`ic_xxx`（小图标）、`img_xxx`（大图片）
4. 项目使用 **ViewBinding**，通过 `inflate` 方式绑定布局
5. 所有 Activity 继承 `BaseActivity`，Fragment 继承 `BaseFragment`
6. 使用 `ScreenMatchLib` 进行屏幕适配，设计图尺寸切换到 **750×1334** 后读取尺寸

### 6.4 网络请求使用流程

1. 在 `lib_coremodel` 的 `data/remote/api` 目录添加 Retrofit API 接口
2. 在 `data/remote/interfaces` 中创建 DataSource 接口
3. 在 `data/remote/service` 目录下创建 API Service（配置请求类型、参数类型、返回数据类型）
4. 在 `data/remote/datasource` 目录下创建 DS 实现类调用 Service 中的请求方法
5. 在 `data/remote/vm` 目录下创建 ViewModel 调用 DataSource 完成网络请求
6. 在 `data/remote/vm/factory` 目录下创建 ViewModel Factory 构建 VM 实例
7. 在 `InjectorUtils` 工具类中暴露给上层提供 ViewModel Factory
8. Activity 中通过 `viewModels` 获取 VM 实例，监听 `HttpResult` 响应

### 6.5 路由使用

项目使用 **NRouter** 进行组件间路由通信，通过 KSP 注解处理器实现。

```kotlin
import com.victor.neuro.router.annotation.Route
import com.victor.lib.common.base.NRouterPath

// 注册路由
@Route(path = NRouterPath.HomeFgt)
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    // ...
}

// 获取路由目标实例
val homeFrag = NeuroRouter.instance.getNavigation(NRouterPath.HomeFgt) as Fragment
```

---

## 七、构建性能优化

项目已在 `gradle.properties` 中配置以下构建优化：

| 优化项 | 配置 | 说明 |
|--------|------|------|
| Gradle Daemon | `org.gradle.daemon=true` | 复用 JVM 实例，减少初始化开销 |
| 并行构建 | `org.gradle.parallel=true` | 多模块并行编译 |
| 构建缓存 | `org.gradle.caching=true` | 缓存构建产物，复用未变更的编译结果 |
| 按需配置 | `org.gradle.configureondemand=true` | 仅配置需要的模块 |
| JVM 内存 | `org.gradle.jvmargs=-Xmx2048m` | 增大 Gradle 守护进程内存 |
| Kotlin 增量编译 | `kotlin.incremental=true` | 开启 Kotlin 增量编译 |
| Kotlin 并行编译 | `kotlin.parallel.tasks.in.project=true` | 模块内并行编译 |
| KSP 增量处理 | `ksp.incremental=true` | KSP 注解处理增量优化 |
| 非传递 R 类 | `android.nonTransitiveRClass=true` | 减少 R 类体积 |
| Core Library Desugaring | 已启用 | 支持 Java 11 API，兼容低版本设备 |

---

## 八、相关文档索引

| 文档 | 路径 | 说明 |
|------|------|------|
| 项目 README | `README.md` | 项目根目录简要说明 |
| 分发脚本 | `docs/upToFir.py` | 自动上传 APK 到 fir.im 测试分发平台 |
| 分发脚本 | `docs/upToPgyer.py` | 自动上传 APK 到蒲公英测试分发平台 |
| 签名密钥 | `docs/victor_keystore.jks` | App 签名密钥文件 |
| 构建配置 | `build-logic/convention/` | Convention Plugin 构建逻辑源码 |
| 版本目录 | `gradle/libs.versions.toml` | 统一依赖版本管理 |
| 构建配置 | `build-logic/convention/src/main/java/com/victor/support/AppBuildConfig.kt` | 编译参数、版本号、模式开关配置 |
| 签名配置 | `build-logic/convention/src/main/java/com/victor/support/AppSignConfig.kt` | 签名文件及密码配置 |
| 分发配置 | `build-logic/convention/src/main/java/com/victor/support/UploadApkConfig.kt` | APK 分发平台 Token 及开关配置 |
