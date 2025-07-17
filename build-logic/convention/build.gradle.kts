plugins {
    `kotlin-dsl`
}

group = "com.victor.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidBase") {
            id = libs.plugins.victor.android.base.get().pluginId
            implementationClass = "com.victor.convention.AndroidBaseConventionPlugin"
        }
        register("androidApplication") {
            id = libs.plugins.victor.android.application.get().pluginId
            implementationClass = "com.victor.convention.AndroidApplicationConventionPlugin"
        }
        register("androidLib") {
            id = libs.plugins.victor.android.lib.get().pluginId
            implementationClass = "com.victor.convention.AndroidLibConventionPlugin"
        }
        register("androidModule") {
            id = libs.plugins.victor.android.module.get().pluginId
            implementationClass = "com.victor.convention.AndroidModuleConventionPlugin"
        }
        register("OpenSource") {
            id = libs.plugins.victor.open.source.get().pluginId
            implementationClass = "com.victor.convention.OpenSourceConventionPlugin"
        }
        register("androidLint") {
            id = libs.plugins.victor.android.lint.get().pluginId
            implementationClass = "com.victor.convention.AndroidLintConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.victor.jvm.library.get().pluginId
            implementationClass = "com.victor.convention.JvmLibraryConventionPlugin"
        }
        register("androidRoom") {
            id = libs.plugins.victor.android.room.get().pluginId
            implementationClass = "com.victor.convention.AndroidRoomConventionPlugin"
        }
    }
}


