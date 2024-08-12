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

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal object Constants {

    object Versions {
        const val COMPILE_SDK = 34
        const val MIN_SDK = 23

        val JAVA = JavaVersion.VERSION_17
        val JVM = JvmTarget.JVM_17
    }

    object CompileArgs {
        const val STRICT_API = "explicit-api=strict"

        private const val COMPOSE = "plugin:androidx.compose.compiler.plugins.kotlin"
        const val COMPOSE_METRICS_PRE = "$COMPOSE:metricsDestination="
        const val COMPOSE_REPORT_PRE = "$COMPOSE:reportsDestination="
        const val COMPOSE_STABILITY_CONFIG_PRE = "$COMPOSE:stabilityConfigurationPath="
    }
}
