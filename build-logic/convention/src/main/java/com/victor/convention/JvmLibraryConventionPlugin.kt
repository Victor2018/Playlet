package com.victor.convention

import com.victor.support.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // 先检查是否已应用 kotlin 插件
//            if (!target.pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")) {
//                apply("org.jetbrains.kotlin.jvm")
//            }
            apply(plugin = "com.victor.convention.lint")

            configureKotlinJvm()
        }
    }
}
