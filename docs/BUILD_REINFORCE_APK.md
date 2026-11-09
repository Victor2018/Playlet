# 编译apk分发分发测试平台使用说明

## 一、命令方式（此方式编译完成后会自动分发到蒲公英、fir.im测试平台）
- 1，不同渠道编译命令

  | 编译环境 | 编译渠道 | 编译类型(buildType) | 编译命令 | 是否自动分发 | 是否自动360加固 |
  |---------|---------|-------------------|---------|------------|---------------|
  | 测试环境 | _test | debug | ./gradlew assemble_TestDebug | 是 | 否 |
  | uat环境 | beta | debug | ./gradlew assembleBetaDebug | 是 | 否 |
  | 线上环境 | online | release | ./gradlew assembleOnlineRelease | 是 | 是 |
  | 线上环境 | huawei | release | ./gradlew assembleHuaweiRelease | 否 | 是 |
  | 线上环境 | xiaomi | release | ./gradlew assembleXiaomiRelease | 否 | 是 |
  | 线上环境 | oppo | release | ./gradlew assembleOppoRelease | 否 | 是 |
  | 线上环境 | vivo | release | ./gradlew assembleVivoRelease | 否 | 是 |
  | 线上环境 | tencent | release | ./gradlew assembleTencentRelease | 否 | 是 |

    注意：
    - buildType为release会出发360自动加固，debug版本不加固减少360加固资源。
    - 加固完成后会自动完成上传，编译apk及加固后的app都在目录project_path\app\build\outputs\apk\对应不同渠道目录
      <img src="http://gitlab.51taodj.com/refact_de/ydj-seller-app/-/raw/test/android/docs/images/apk_out.png?inline=false" /><br>

- 2，通过AndroidStudio Build/Generate Signed App Bundle or APK方式打包后需要在UploadApkConfig配置上传环境手动分发

    - 不同环境配置说明
  
  | 编译环境 | UPLOAD_CHANNEL_BUILD_TYPE |
  |---------|--------------------------|
  | 测试环境 | 2 |
  | uat环境 | 3 |
  | 线上环境 | 5 |
    - 手动分发命令如下：
      ./gradlew apkUpload
    - 通过AndroidStudio Tasks 找到app/Tasks/other/apkUpload双击执行也是一样的效果