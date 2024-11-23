/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.carbon.buildlogic

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

/**
 * Configure Kotlin for Android projects.
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
