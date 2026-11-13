package com.victor.support

import org.gradle.api.JavaVersion
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object AppBuildConfig {
    /**
     * isModule是“集成开发模式”和“组件开发模式”的切换开关
     */
    const val isModule = false

    const val compileSdk = 34
    const val minSdk = 24
    const val targetSdk = 34
    val javaVersion = JavaVersion.VERSION_17

    /**
     * 编译apk文件名称
     */
    const val BUILD_NAME = "playlet"

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

    fun getBuildDate(): String {
        return LocalDateTime.now(ZoneId.of("GMT+8"))
            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd"))
    }
}