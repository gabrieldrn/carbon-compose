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

package com.gabrieldrn.themesmodel

import com.gabrieldrn.themesmodel.model.Theme
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath

private const val file_path_format = "code-gen/themes-model/src/commonMain/resources/%s.json"

private val themes = listOf(
    "white",
    "g10",
    "g90",
    "g100",
)

internal expect val fileSystem: FileSystem

public fun deserializeColorTokens(): Map<String, Theme> = themes.associateWith { theme ->
    Json.decodeFromString<Theme>(
        fileSystem.read(
            file_path_format.replace("%s", theme).toPath()
                .also { println(it) }
        ) { readUtf8() }
    )
}
