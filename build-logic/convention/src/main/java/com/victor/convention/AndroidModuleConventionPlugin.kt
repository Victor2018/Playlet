package com.victor.convention

import com.android.build.api.dsl.LibraryExtension
import com.victor.support.AppBuildConfig
import com.victor.support.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import java.util.Locale

class AndroidModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            if (AppBuildConfig.isModule) {
                apply(plugin = "com.android.application")
            } else {
                apply(plugin = "com.android.library")
            }

            apply(plugin = "com.victor.convention.base")
            apply(plugin = "com.victor.convention.android.room")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                //给 Module 内的资源名增加前缀, 避免资源名冲突
                resourcePrefix = "${
                    project.name.lowercase(Locale.getDefault()).replace("module_", "")}_"

                //这里进行设置使用单独运行还是合并运行的Manifest.xml
                sourceSets {
                    getByName("main") {
                        jniLibs.srcDirs("libs")  // 设置jni库目录
                        if (AppBuildConfig.isModule) {
                            manifest.srcFile("src/main/module/AndroidManifest.xml")
                        } else {
                            manifest.srcFile("src/main/AndroidManifest.xml")
                        }
                    }
                }
            }

            dependencies {
                "api"(project(":lib_common"))
            }
        }
    }

}