@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "com.gabrieldrn.carbon.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.compose.compiler)
    compileOnly(libs.dokka.plugin)
}

gradlePlugin {
    plugins {
        register("kmpLibrary") {
            id = "carbon.kmp.library"
            implementationClass = "CarbonMultiplatformLibraryConventionPlugin"
        }

        register("application") {
            id = "carbon.android.application"
            implementationClass = "CarbonApplicationConventionPlugin"
        }

        register("detekt") {
            id = "carbon.detekt"
            implementationClass = "CarbonDetektConventionPlugin"
        }

        register("dokka") {
            id = "carbon.dokka"
            implementationClass = "CarbonDokkaConventionPlugin"
        }
    }
}
