import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.sourceSets

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
    alias(libs.plugins.kotlin.serialization)
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
    }
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ThemesModel"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.okio)
            implementation(kotlin("reflect"))
        }
    }
}

android {
    namespace = "com.gabrieldrn.themesmodel"
}