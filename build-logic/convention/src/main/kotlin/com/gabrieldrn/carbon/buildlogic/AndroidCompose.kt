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

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerToolOptions

internal fun KotlinCommonCompilerToolOptions.setupComposeCompilerOptions(project: Project) =
    with(project) {
        freeCompilerArgs.addAll(
            "-P",
            Constants.CompileArgs.COMPOSE_METRICS_PRE +
                "\"${layout.buildDirectory.get()}/compose/metrics\"",
            "-P",
            Constants.CompileArgs.COMPOSE_REPORT_PRE +
                "\"${layout.buildDirectory.get()}/compose/reports\"",
        )
        file("${projectDir.absolutePath}/compose_compiler_config.conf")
            .takeIf { it.exists() }
            ?.let {
                freeCompilerArgs.addAll(
                    "-P",
                    Constants.CompileArgs.COMPOSE_STABILITY_CONFIG_PRE + it
                )
            }
    }
