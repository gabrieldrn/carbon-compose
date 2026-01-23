pluginManagement {
    includeBuild("./build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Carbon"

include(
    ":catalog",
    ":catalog-android",
    ":carbon",
    ":carbon:common",
    ":carbon:test",
    ":code-gen",
    "doc-parser",
)
