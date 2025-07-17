plugins {
    alias(libs.plugins.victor.android.application)
    alias(libs.plugins.victor.jvm.library)
}

android {
    namespace = "com.victor.playlet"

    defaultConfig {
        applicationId = "com.victor.playlet"
        versionCode = 1
        versionName = "1.0"

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
}