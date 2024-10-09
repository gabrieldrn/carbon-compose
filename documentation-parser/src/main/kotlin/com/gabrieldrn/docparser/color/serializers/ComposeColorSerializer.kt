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

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Locale

object ComposeColorSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Color", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Color) {
        val hex = String.format(
            locale = Locale.getDefault(),
            format = "#%02x%02x%02x",
            value.red.toInt(),
            value.green.toInt(),
            value.blue.toInt()
        )
        val alphaPercent = (value.alpha * 100).toInt()
        encoder.encodeString("$hex @ $alphaPercent%")
    }

    override fun deserialize(decoder: Decoder): Color {
        val string = decoder.decodeString()
        val hex = string.substringBefore(" @")
        val alphaPercent = string.substringAfter("@ ", missingDelimiterValue = "100%")
        val alpha = (alphaPercent.removeSuffix("%").toInt() / 100f * 255).toInt()
        val red = hex.substring(1, 3).toInt(16)
        val green = hex.substring(3, 5).toInt(16)
        val blue = hex.substring(5, 7).toInt(16)
        return Color(red, green, blue, alpha)
    }
}
