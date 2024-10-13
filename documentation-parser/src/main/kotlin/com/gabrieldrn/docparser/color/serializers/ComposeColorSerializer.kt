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

package com.gabrieldrn.docparser.color.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ComposeColorSerializer : KSerializer<String> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Color", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        val alpha = value.substring(1..2).toInt(16)
        val hex = "#${value.substring(3)}"
        val alphaPercent = (alpha / 255f * 100).toInt()
        encoder.encodeString("$hex @ $alphaPercent%")
    }

    override fun deserialize(decoder: Decoder): String {
        val string = decoder.decodeString()
        val hex = string.substringBefore(" @")
        val alphaPercent = string.substringAfter("@ ", missingDelimiterValue = "100%")
        val alpha = (alphaPercent.removeSuffix("%").toInt() / 100f * 255)
            .toInt()
            .let { "%02x".format(it) }
        val red = hex.substring(1, 3)
        val green = hex.substring(3, 5)
        val blue = hex.substring(5, 7)
        return "#$alpha$red$green$blue".uppercase()
    }
}
