package com.victor.support

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileSdk = AppConfig.compileSdk

        defaultConfig {
            minSdk = AppConfig.minSdk

            splits {
                abi {
                    isEnable = false // 禁用 ABI 分包
                    reset()
                    include("arm64-v8a", "armeabi-v7a") // 可选：明确包含需要的 ABI
                }
            }
        }

        buildTypes {
            getByName("release"){
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            // Up to Java 11 APIs are available through desugaring
            // https://developer.android.com/studio/write/java11-minimal-support-table
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }

        // 启用 ViewBinding
        buildFeatures {
            viewBinding = true
        }

        sourceSets {
            getByName("main") {
                jniLibs.srcDirs("libs")  // 设置jni库目录
            }
        }

        buildFeatures {
            buildConfig = true  // 启用 BuildConfig 生成
        }

        flavorDimensions += "hok"
        productFlavors {
            create("beta") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "false")
                buildConfigField("Boolean", "MODEL_BETA", "true")
                buildConfigField("Boolean", "MODEL_DEV", "false")
                buildConfigField("Boolean", "MODEL_TEST", "false")
                buildConfigField("String", "UMENG_CHANNEL", "\"uat\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "uat"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }

            create("dev") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "false")
                buildConfigField("Boolean", "MODEL_BETA", "false")
                buildConfigField("Boolean", "MODEL_DEV", "true")
                buildConfigField("Boolean", "MODEL_TEST", "false")
                buildConfigField("String", "UMENG_CHANNEL", "\"dev\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "dev"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }

            create("_test") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "false")
                buildConfigField("Boolean", "MODEL_BETA", "false")
                buildConfigField("Boolean", "MODEL_DEV", "false")
                buildConfigField("Boolean", "MODEL_TEST", "true")
                buildConfigField("String", "UMENG_CHANNEL", "\"_test\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "_test"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }

            create("online") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "true")
                buildConfigField("Boolean", "MODEL_BETA", "false")
                buildConfigField("Boolean", "MODEL_DEV", "false")
                buildConfigField("Boolean", "MODEL_TEST", "false")
                buildConfigField("String", "UMENG_CHANNEL", "\"online\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "online"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }

            create("huawei") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "true")
                buildConfigField("Boolean", "MODEL_BETA", "false")
                buildConfigField("Boolean", "MODEL_DEV", "false")
                buildConfigField("Boolean", "MODEL_TEST", "false")
                buildConfigField("String", "UMENG_CHANNEL", "\"huawei\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "huawei"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }

            create("xiaomi") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "true")
                buildConfigField("Boolean", "MODEL_BETA", "false")
                buildConfigField("Boolean", "MODEL_DEV", "false")
                buildConfigField("Boolean", "MODEL_TEST", "false")
                buildConfigField("String", "UMENG_CHANNEL", "\"xiaomi\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "xiaomi"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }

            create("oppo") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "true")
                buildConfigField("Boolean", "MODEL_BETA", "false")
                buildConfigField("Boolean", "MODEL_DEV", "false")
                buildConfigField("Boolean", "MODEL_TEST", "false")
                buildConfigField("String", "UMENG_CHANNEL", "\"oppo\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "oppo"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }

            create("vivo") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "true")
                buildConfigField("Boolean", "MODEL_BETA", "false")
                buildConfigField("Boolean", "MODEL_DEV", "false")
                buildConfigField("Boolean", "MODEL_TEST", "false")
                buildConfigField("String", "UMENG_CHANNEL", "\"vivo\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "vivo"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }

            create("tencent") {
                dimension = "hok"
                buildConfigField("Boolean", "MODEL_ONLINE", "true")
                buildConfigField("Boolean", "MODEL_BETA", "false")
                buildConfigField("Boolean", "MODEL_DEV", "false")
                buildConfigField("Boolean", "MODEL_TEST", "false")
                buildConfigField("String", "UMENG_CHANNEL", "\"tencent\"")

                manifestPlaceholders["BUGLY_APPID"] = AppBuildConfig.BUGLY_APP_ID
                manifestPlaceholders["BUGLY_APP_VERSION"] = AppBuildConfig.VERSION_NAME
                manifestPlaceholders["BUGLY_APP_CHANNEL"] = "tencent"
                manifestPlaceholders["BUGLY_ENABLE_DEBUG"] = "false"
            }
        }

    }

    configureKotlin()

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        // Up to Java 11 APIs are available through desugaring
        // https://developer.android.com/studio/write/java11-minimal-support-table
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    configureKotlin()
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin() {
    // Use withType to workaround https://youtrack.jetbrains.com/issue/KT-55947
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            // Set JVM target to 11
            jvmTarget = JavaVersion.VERSION_11.toString()
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
            )
        }
    }
}