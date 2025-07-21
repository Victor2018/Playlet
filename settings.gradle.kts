pluginManagement {
    includeBuild("build-logic") // 关键！启用约定插件
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://www.jitpack.io")

        flatDir {
            dirs("lib_coremodel/src/main/libs")
        }
    }
}

rootProject.name = "Playlet"
include(":app")
include(":lib_coremodel")
include(":lib_common")
include(":module_home")
include(":module_me")
include(":module_theater")
include(":module_welfare")
