package com.victor.support

object AppBuildConfig {
    /**
     * isModule是“集成开发模式”和“组件开发模式”的切换开关
     */
    const val isModule = false

    const val compileSdk = 35
    const val minSdk = 24
    const val targetSdk = 35

    /**
     * bugly app id
     */
    const val BUGLY_APP_ID = "1015ead9fa"

    /**
     * 编译apk文件名称
     */
    const val BUILD_NAME = "1015ead9fa"

    /**
     * 正式版本名称
     */
    const val VERSION_NAME = "1.0.0"

    /**
     * 正式版本号
     */
    const val VERSION_CODE = 1

    /**
     * # 编译版本每次+1
     */
    const val BUILD_NUMBER = 1
}