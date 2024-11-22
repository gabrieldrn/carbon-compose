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

package com.gabrieldrn.codegen.color

import com.gabrieldrn.codegen.color.model.colortokens2.Theme
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

const val file_path_format = "/%s.json"

private val themes = listOf(
    "white",
    "g10",
    "g90",
    "g100",
)

@OptIn(ExperimentalSerializationApi::class)
fun deserializeColorTokens(): Map<String, Theme> = themes.associateWith { theme ->
    object {}::class.java
        .getResourceAsStream(file_path_format.format(theme))
        .use { stream ->
            try {
                Json.decodeFromStream<Theme>(stream)
            } catch (e: Exception) {
                error("Could not load theme $theme." + e.message)
            }
        }
}
