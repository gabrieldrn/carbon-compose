@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "carbon.compose.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kmpLibrary") {
            id = "carbon.kmp.library"
            implementationClass = "CarbonMultiplatformLibraryConventionPlugin"
        }

        register("androidLibrary") {
            id = "carbon.android.library"
            implementationClass = "CarbonAndroidLibraryConventionPlugin"
        }

        // Not used yet, but it's here for potential future use.
        register("javaLibrary") {
            id = "carbon.java.library"
            implementationClass = "CarbonJavaLibraryConventionPlugin"
        }

        register("application") {
            id = "carbon.android.application"
            implementationClass = "CarbonApplicationConventionPlugin"
        }

        register("detekt") {
            id = "carbon.detekt"
            implementationClass = "CarbonDetektConventionPlugin"
        }

        // TODO Publication plugin
        // TODO Dokka plugin
    }
}
