package com.victor.convention

import com.victor.support.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidBaseConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin ="org.jetbrains.kotlin.android")
            apply(plugin = "com.google.devtools.ksp")

            dependencies {
                "api"(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
                "api"(libs.findLibrary("androidx.core.ktx").get())
                "api"(libs.findLibrary("androidx.appcompat").get())
                "api"(libs.findLibrary("material").get())
                "testApi"(libs.findLibrary("junit").get())
                "androidTestApi"(libs.findLibrary("androidx.junit").get())
                "androidTestApi"(libs.findLibrary("androidx.espresso.core").get())
                "api"(libs.findLibrary("arouter.api").get())
                "ksp"(libs.findLibrary("arouter.compiler").get())
            }
        }
    }
}
