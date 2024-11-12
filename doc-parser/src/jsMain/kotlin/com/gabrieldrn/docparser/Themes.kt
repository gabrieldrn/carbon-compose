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

package com.gabrieldrn.docparser

@JsModule("@carbon/themes")
@JsNonModule
external val carbonThemes: dynamic

val themes = listOf("g10", "g90", "g100", "white")
val components = listOf("buttonTokens", "tagTokens", "notificationTokens")

val missingTokens = mapOf(
    "notificationActionHover" to mapOf(
        "g90" to "#474747",
        "g100" to "#333333",
    )
)

fun entries(obj: dynamic) = js("Object.entries(obj)") as Array<dynamic>

fun Map<String, String>.filterAndFormatTokens() =
    filter { (_, v) -> v.startsWith("#") || v.startsWith("rgba") }
        .mapValues { (_, v) -> if (v.startsWith('#')) v.uppercase() else v }

@Suppress("UNCHECKED_CAST")
fun Map<String, Any>.formatToString(): String {
    return map {
        val value = when (val v = it.value) {
            is String -> "\"$v\""
            is Map<*, *> -> (v as? Map<String, Any>)?.formatToString()
                ?: error("Unexpected value type")
            else -> error("Unexpected value type")
        }

        "\"${it.key}\": $value"
    }
        .joinToString(separator = ", ", prefix = "{ ", postfix = " }")
}

fun main() {
    themes.forEach { themeName ->
        val themeObject = carbonThemes[themeName]
        val themeTokens = entries(themeObject)
            .associate { it[0] as String to it[1] }
            .filter { (_, v) -> v is String }
            .filterAndFormatTokens()

        // color reference in component for the white theme are mapped to "whiteTheme".
        val themeNameForComponents = if (themeName == "white") "whiteTheme" else themeName

        val components = components.associateWith { component ->
            entries(carbonThemes[component][component])
                .associate { it[0] as String to it[1] }
                .mapValues { (k, v) ->
                    val value = v[themeNameForComponents]

                    if (js("value === undefined") as Boolean) {
                        missingTokens[k]?.get(themeName)
                            ?: error("Missing token for $k in $themeName")
                    } else {
                        value as String
                    }
                }
                .filterAndFormatTokens()
        }

        val asJson = themeTokens
            .toMutableMap<String, Any>()
            .apply { putAll(components) }
            .formatToString()

        println("Theme $themeName: $asJson")
    }
}
