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

    applicationVariants.all {
        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            outputImpl.outputFileName = "${rootProject.name}_${productFlavors[0].name}_${buildType.name}_v" +
                    "${defaultConfig.versionName}_${defaultConfig.versionCode}.apk"
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