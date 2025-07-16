plugins {
    `kotlin-dsl`
}

group = "com.victor.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
//    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.victor.android.application.get().pluginId
            implementationClass = "com.victor.convention.AndroidApplicationConventionPlugin"
        }
        register("androidLint") {
            id = libs.plugins.victor.android.lint.get().pluginId
            implementationClass = "com.victor.convention.AndroidLintConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.victor.jvm.library.get().pluginId
            implementationClass = "com.victor.convention.JvmLibraryConventionPlugin"
        }
    }
}


