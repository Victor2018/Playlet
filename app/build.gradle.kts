import com.victor.support.AppBuildConfig
import com.victor.support.TestApkDistribution.manualDistributeTestApk

plugins {
    alias(libs.plugins.victor.android.application)
    alias(libs.plugins.victor.jvm.library)
}

android {
    namespace = "com.victor.playlet"

    defaultConfig {
        applicationId = "com.victor.playlet"
        versionCode = AppBuildConfig.VERSION_CODE
        versionName = AppBuildConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    applicationVariants.all {
        val flavor = this.flavorName
        val buildType = this.buildType.name
        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            outputImpl.outputFileName = "${AppBuildConfig.BUILD_NAME}_${flavor}_${buildType}" +
                    "_v${AppBuildConfig.VERSION_NAME}_${AppBuildConfig.VERSION_CODE}" +
                    "_${AppBuildConfig.getBuildDate()}_${AppBuildConfig.BUILD_NUMBER}.apk"
        }
    }
}

dependencies {
    if (AppBuildConfig.isModule) {
        implementation(project(":lib_common"))
        implementation(project(":lib_coremodel"))
    } else {
        implementation(project(":module_home"))
        implementation(project(":module_theater"))
        implementation(project(":module_welfare"))
        implementation(project(":module_me"))
    }
}

tasks.register("apkUpload") {
    doLast {
        println("---------------------buildFinished---------------------")
        //手动分发测试apk
        manualDistributeTestApk()
    }
}