package com.victor.convention

import com.android.build.api.dsl.ApplicationExtension
import com.victor.support.AppBuildConfig
import com.victor.support.AppSignConfig
import com.victor.support.BuildFinishService
import com.victor.support.configureKotlinAndroid
import com.victor.support.TestApkDistribution.autoDistributeTestApk
import com.victor.support.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.build.event.BuildEventsListenerRegistry
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import javax.inject.Inject


class AndroidApplicationConventionPlugin @Inject constructor(
    private val buildEventsListenerRegistry: BuildEventsListenerRegistry
) : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin ="com.android.application")
            apply(plugin = "com.victor.convention.base")
            apply(plugin = "com.victor.convention.android.room")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AppBuildConfig.targetSdk

                signingConfigs {
                    create("release") {
                        storeFile = file(AppSignConfig.STORE_FILE)
                        keyAlias = AppSignConfig.KEY_ALIAS
                        keyPassword = AppSignConfig.KEY_PASSWORD
                        storePassword = AppSignConfig.STORE_PASSWORD
                    }
                }

                buildTypes {
                    debug {
                        signingConfig = signingConfigs.getByName("release")
                    }
                    release {
                        signingConfig = signingConfigs.getByName("release")
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }

            dependencies {
                "implementation"(libs.findLibrary("androidx.constraintlayout").get())
                "implementation"(libs.findLibrary("androidx.navigation.fragment.ktx").get())
                "implementation"(libs.findLibrary("androidx.navigation.ui.ktx").get())
            }

            gradle.settingsEvaluated {
                println("---------------------settingsEvaluated---------------------")
            }

            gradle.projectsLoaded {
                println("---------------------projectsLoaded---------------------")
            }

            gradle.projectsEvaluated {
                println("---------------------projectsEvaluated---------------------")
            }

            // 构建结束时回调，替代已废弃的 BuildListener#buildFinished
            val buildFinishService = gradle.sharedServices.registerIfAbsent(
                "victorBuildFinishService",
                BuildFinishService::class.java
            )
            buildFinishService.get().onBuildFinish {
                // 只在实际执行目标 assembleOnlineRelease 线上环境任务执行完成时才触发自动分发，其他环境分发手动执行 apkUpload task 完成分发
                val taskNames = gradle.startParameter.taskNames
                println("---------------------buildFinished$taskNames---------------------")
                autoDistributeTestApk(taskNames)
            }
            // 注册为任务事件监听者，确保服务实例被创建并在构建结束时关闭
            buildEventsListenerRegistry.onTaskCompletion(buildFinishService)
        }
    }

}