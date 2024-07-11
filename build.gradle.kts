buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.screenshot) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.binaryCompatibilityValidator)
    alias(libs.plugins.vanniktech.publish.plugin)
}

apiValidation {
    /**
     * Sub-projects that are excluded from API validation
     */
    ignoredProjects.addAll(listOf("catalog", "code-gen"))
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.layout.buildDirectory)
//}
