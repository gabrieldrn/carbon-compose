package com.gabrieldrn.codegen.color.serializers

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeColorSerializerTest {

    @Test
    fun `test serialize color with alpha`() {
        // Given a color with an alpha channel
        val color = "#80FF5733" // alpha=80 (50%), color=FF5733

        // When the color is serialized
        val serialized = Json.encodeToString(ComposeColorSerializer, color)

        // Then the serialized string should match the expected format
        assertEquals(""""#FF5733 @ 50%"""", serialized)
    }

    @Test
    fun `test deserialize color with alpha`() {
        // Given a serialized color string
        val serializedColor = """"#FF5733 @ 50%""""

        // When the string is deserialized
        val deserialized = Json.decodeFromString(ComposeColorSerializer, serializedColor)

        // Then the deserialized string should match the original hex color
        assertEquals("#80FF5733", deserialized) // alpha=80 (50%), color=FF5733
    }

    @Test
    fun `test serialize color with full opacity`() {
        // Given a color with full opacity
        val color = "#FFFF5733" // alpha=FF (100%), color=FF5733

        // When the color is serialized
        val serialized = Json.encodeToString(ComposeColorSerializer, color)

        // Then the serialized string should represent full opacity
        assertEquals(""""#FF5733 @ 100%"""", serialized)
    }

    @Test
    fun `test deserialize color with full opacity`() {
        // Given a serialized color string with full opacity
        val serializedColor = """"#FF5733 @ 100%""""

        // When the string is deserialized
        val deserialized = Json.decodeFromString(ComposeColorSerializer, serializedColor)

        // Then the deserialized string should match the original hex color with full alpha
        assertEquals("#FFFF5733", deserialized) // alpha=FF (100%), color=FF5733
    }
}
