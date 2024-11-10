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

package com.gabrieldrn.codegen.color.model.colortokens

import com.gabrieldrn.codegen.color.serializers.ComposeColorSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ColorToken(
    val white: TokenValue,
    val g10: TokenValue,
    val g90: TokenValue,
    val g100: TokenValue
) {
    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    data class TokenValue(
        val name: String,
        @Serializable(with = ComposeColorSerializer::class)
        @JsonNames("hex")
        val color: String
    )
}
