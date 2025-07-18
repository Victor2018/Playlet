plugins {
    alias(libs.plugins.victor.android.lib)
}
android {
    namespace = "com.victor.lib.common"
}
dependencies {
    api(project(":lib_coremodel"))
}