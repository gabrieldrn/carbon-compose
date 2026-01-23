plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "com.gabrieldrn.carbon.buildlogic"

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.compose.compiler)
    compileOnly(libs.dokka.plugin)
    compileOnly(libs.vanniktechPublishPlugin)
}

gradlePlugin {
    plugins {
        register("kmpLibrary") {
            id = "carbon.kmp.library"
            implementationClass = "CarbonMultiplatformLibraryConventionPlugin"
        }

        register("androidApplication") {
            id = "carbon.android.application"
            implementationClass = "CarbonApplicationConventionPlugin"
        }

        register("kmpApplication") {
            id = "carbon.kmp.application"
            implementationClass = "CarbonKmpApplicationConventionPlugin"
        }

        register("detekt") {
            id = "carbon.detekt"
            implementationClass = "CarbonDetektConventionPlugin"
        }
    }
}
