package carbon.compose.buildlogic

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

/**
 * Configure Android extension with common settings.
 */
internal fun CommonExtension<*, *, *, *, *, *>.configureKotlinAndroidCommon() {

    compileSdk = Constants.Versions.COMPILE_SDK
    defaultConfig {
        minSdk = Constants.Versions.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = Constants.Versions.JAVA
        targetCompatibility = Constants.Versions.JAVA
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(Constants.Versions.JVM)
        }
    }

    buildFeatures {
        compose = true
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
