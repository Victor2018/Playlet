package com.victor.convention

import com.victor.support.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class OpenSourceConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "api"(libs.findBundle("victor").get())
                "api"(libs.findLibrary("multidex.core").get())
                "api"(libs.findLibrary("androidx.swiperefreshlayout").get())
                "api"(libs.findBundle("coroutines").get())
                "testApi"(libs.findLibrary("coroutines.test").get())
                "api"(libs.findLibrary("glide.runtime").get())
                "ksp"(libs.findLibrary("glide.compiler").get())
                "api"(libs.findBundle("lifecycle").get())
                "api"(libs.findBundle("retrofit").get())
                "api"(libs.findBundle("okhttp3").get())
                "api"(libs.findBundle("umeng").get())
                "api"(libs.findLibrary("picture.selector").get())
                "api"(libs.findLibrary("lottie.core").get())
                "api"(libs.findLibrary("zxing.core").get())
                "api"(libs.findLibrary("bugly.core").get())
                "api"(libs.findLibrary("wechat.sdk").get())
            }
        }
    }
}
