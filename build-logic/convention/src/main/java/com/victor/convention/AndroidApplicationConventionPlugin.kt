package com.victor.convention

import com.android.build.api.dsl.ApplicationExtension
import com.victor.support.AppBuildConfig
import com.victor.support.AppSignConfig
import com.victor.support.configureKotlinAndroid
import com.victor.support.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
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
        }
    }

}