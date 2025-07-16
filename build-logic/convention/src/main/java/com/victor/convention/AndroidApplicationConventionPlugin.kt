package com.victor.convention

import com.android.build.api.dsl.ApplicationExtension
import com.victor.support.AppConfig
import com.victor.support.AppSignConfig
import com.victor.support.configureKotlinAndroid
import com.victor.support.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")

                apply("com.victor.convention.base")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AppConfig.targetSdk

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