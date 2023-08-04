buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka) apply false //Globally disabled
    alias(libs.plugins.binaryCompatibilityValidator)
}

tasks.named<io.gitlab.arturbosch.detekt.Detekt>("detekt").configure {
    reports {
        xml {
            required.set(true)
            outputLocation.set(file("build/reports/detekt/detekt.xml"))
        }
        html {
            required.set(true)
            outputLocation.set(file("build/reports/detekt/detekt.html"))
        }
        txt.required.set(false)
        sarif.required.set(false)
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        // Version of Detekt that will be used. When unspecified the latest detekt
        // version found will be used. Override to stay on the same version.
        toolVersion = "1.22.0"

        // Builds the AST in parallel. Rules are always executed in parallel.
        // Can lead to speedups in larger projects. `false` by default.
        parallel = false

        // Define the detekt configuration(s) you want to use.
        // Defaults to the default detekt configuration.
        config = files("$rootDir/config/detekt/detekt.yml")

        // Turns on all the rules. `false` by default.
        allRules = false

        // Adds debug output during task execution. `false` by default.
        debug = false

        // If set to `true` the build does not fail when the
        // maxIssues count was reached. Defaults to `false`.
        ignoreFailures = true

        // Android: Don't create tasks for the specified build types (e.g. "release")
//        ignoredBuildTypes = ["release"]

        // Android: Don't create tasks for the specified build flavor (e.g. "production")
//        ignoredFlavors = ["production"]

        // Android: Don't create tasks for the specified build variants (e.g. "productionRelease")
//        ignoredVariants = ["productionRelease"]

        // Specify the base path for file paths in the formatted reports.
        // If not set, all file paths reported will be absolute file path.
        basePath = projectDir.absolutePath
    }
}

apiValidation {
    /**
     * Sub-projects that are excluded from API validation
     */
    ignoredProjects.addAll(listOf("catalog"))
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
