plugins {
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.victor.playlet"
        minSdk = 24
        targetSdk = 35

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
    room {
        schemaDirectory ("$projectDir/schemas")
    }
}

dependencies {
    // 使用fileTree来包含libs目录下的所有.jar文件
    implementation(fileTree("libs") {
        include("*.jar")
    })
    //room
    ksp(libs.room.compiler)
    implementation(libs.bundles.room)
}