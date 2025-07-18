import com.victor.support.AppBuildConfig

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
}

dependencies {
    if (AppBuildConfig.isModule) {
        implementation(project(":lib_common"))
        implementation(project(":lib_coremodel"))
    } else {
        implementation(project(":module_home"))
//        implementation(":module_login")
//        implementation(":module_me")
    }
}