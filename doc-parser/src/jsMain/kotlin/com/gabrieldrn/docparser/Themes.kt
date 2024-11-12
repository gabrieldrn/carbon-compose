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

@JsModule("fs")
@JsNonModule
external val fs: dynamic

val themes = listOf("g10", "g90", "g100", "white")
val components = listOf("buttonTokens", "tagTokens", "notificationTokens")

val missingTokens = mapOf(
    "notificationActionHover" to mapOf(
        "g90" to "#474747",
        "g100" to "#333333",
    ),
//    "buttonDisabled" to mapOf(
//        "g90" to "rgba(141, 141, 141, 0.30)",
//        "g100" to "rgba(141, 141, 141, 0.30)"
//    )
)

fun entries(obj: dynamic) = js("Object.entries(obj)") as Array<dynamic>

fun String.formatColor(): String {
    fun formatHex(hex: String) = js("hex.toString(16).padStart(2, '0')") as String
    return when {
        startsWith("#") -> replace("#", "#FF")
        // rgb(141 141 141 / 30%)
        startsWith("rgb(") -> removeSurrounding("rgb(", ")")
            .split(" ")
            .filterNot { it == "/" }
            .map {
                if (it.endsWith("%")) {
                    (it.removeSuffix("%").toFloat() / 100 * 255).toInt()
                } else {
                    it.toInt()
                }
                    .toString(16)
                    .let(::formatHex)
            }
            .let { (r, g, b, a) -> "#$a$r$g$b" }

        startsWith("rgba(") -> removeSurrounding("rgba(", ")")
            .split(", ")
            .map {
                if (it.contains(".")) {
                    (it.toFloat() * 255).toInt()
                } else {
                    it.toInt()
                }
                    .toString(16)
                    .let(::formatHex)
            }
            .let { (r, g, b, a) -> "#$a$r$g$b" }
        else -> error("Unexpected color format")
    }
        .uppercase()
}

fun Map<String, String>.filterAndFormatTokens() =
    filter { (_, v) -> v.startsWith("#") || v.startsWith("rgba(") || v.startsWith("rgb(") }
        .mapValues { (_, v) -> v.formatColor() }

@Suppress("UNCHECKED_CAST")
fun Map<String, Any>.formatToString(): String {
    return map {
        val value = when (val v = it.value) {
            is String -> "\"$v\""
            is Map<*, *> -> (v as? Map<String, Any>)?.formatToString()
                ?: error("Unexpected value type")
            else -> error("Unexpected value type")
        }

        "\"${it.key}\":$value"
    }
        .joinToString(separator = ",", prefix = "{", postfix = "}")
}

/**
 * Regroups every color token for each theme and component and extract them as JSON files.
 *
 * The JSON are written to the resources folder of the code-gen module to generate the theme classes
 * implementations.
 */
fun main() {
    println((carbonThemes.buttonTokens.buttonTokens.buttonDisabled.g90 as String).formatColor())


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

        fs.writeFile(
            "../../../../code-gen/src/main/resources/$themeName.json",
            asJson
        ) { err: dynamic ->
            if (err != null) console.error("Error writing file: $err")
        }

        console.log("Theme $themeName processed")
    }
}
