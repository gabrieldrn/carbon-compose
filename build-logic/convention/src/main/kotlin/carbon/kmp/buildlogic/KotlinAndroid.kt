package carbon.kmp.buildlogic

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

/**
 * Configure Android extension with common settings.
 */
internal fun CommonExtension<*, *, *, *, *>.configureKotlinAndroidCommon() {

    compileSdk = Constants.Versions.COMPILE_SDK
    defaultConfig {
        minSdk = Constants.Versions.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = Constants.Versions.JAVA
        targetCompatibility = Constants.Versions.JAVA
    }

    kotlinOptions {
        jvmTarget = Constants.Versions.JAVA.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Constants.Versions.COMPOSE_COMPILER
    }
}

/**
 * Setup explicit api for all kotlin compile tasks except tests and debug.
 * @see <a href="https://github.com/Kotlin/KEEP/blob/master/proposals/explicit-api-mode.md">
 *     Explicit API mode
 * </a>
 */
internal fun Project.setupExplicitApi() {
    tasks.withType(KotlinCompile::class.java)
        .matching { !it.name.contains("test", ignoreCase = true) }
        .also {
            logger.quiet("Configuring explicit API for tasks ${it.map { t -> t.path }}")
        }
        .matching {
            it.kotlinOptions
                .freeCompilerArgs
                .contains("-X" + Constants.CompileArgs.STRICT_API)
                .not()
        }
        .configureEach {
            kotlinOptions.freeCompilerArgs += listOf("-X" + Constants.CompileArgs.STRICT_API)
        }
}


@Suppress("UnstableApiUsage")
internal fun LibraryExtension.applyTestOptions() {
    testOptions {
        // Somehow this must be specified for instrumentation tests to work. If not, the
        // Android system warns that the generated app is made for a lower API level.
        targetSdk = Constants.Versions.COMPILE_SDK

        unitTests.all {
            it.testLogging {
                events = setOf(
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.FAILED,
//                        TestLogEvent.STANDARD_OUT,
//                        TestLogEvent.STANDARD_ERROR
                )
                exceptionFormat = TestExceptionFormat.FULL
                showStandardStreams = true
                showExceptions = true
                showCauses = true
                showStackTraces = true
                showStandardStreams = true
            }
        }
    }
}
