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

package com.gabrieldrn.codegen.color.serializers

import com.gabrieldrn.codegen.color.model.colortokens.Theme
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonObject

/**
 * Carbon's theme JSON has syntax color tokens (`syntaxKeyword`, `syntaxComment`, ...) flattened
 * at the root, unlike other components (`buttonColors`, `chatColors`, ...) which are already
 * nested objects. This serializer nests them under a synthetic `syntaxColors` key so they
 * deserialize into [com.gabrieldrn.codegen.color.model.colortokens.SyntaxColors].
 */
object ThemeSerializer : JsonTransformingSerializer<Theme>(Theme.generatedSerializer()) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        val (syntaxEntries, otherEntries) = element.jsonObject.entries
            .partition { it.key.startsWith("syntax") }

        return JsonObject(
            otherEntries.associate { it.key to it.value } +
                mapOf("syntaxColors" to JsonObject(syntaxEntries.associate { it.key to it.value }))
        )
    }
}
