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

package com.gabrieldrn.codegen

import com.gabrieldrn.codegen.color.abstractThemeDoc
import com.gabrieldrn.codegen.color.deserializeColorTokens
import com.gabrieldrn.codegen.color.g100ThemeDoc
import com.gabrieldrn.codegen.color.g10ThemeDoc
import com.gabrieldrn.codegen.color.g90ThemeDoc
import com.gabrieldrn.codegen.color.model.colortokens.AiColors
import com.gabrieldrn.codegen.color.model.colortokens.ButtonColors
import com.gabrieldrn.codegen.color.model.colortokens.ChatColors
import com.gabrieldrn.codegen.color.model.colortokens.NotificationColors
import com.gabrieldrn.codegen.color.model.colortokens.SyntaxColors
import com.gabrieldrn.codegen.color.model.colortokens.TagColors
import com.gabrieldrn.codegen.color.model.colortokens.Theme
import com.gabrieldrn.codegen.color.whiteThemeDoc
import com.gabrieldrn.codegen.common.TokenSetResult
import com.gabrieldrn.codegen.common.generateTokenSetClass
import com.squareup.kotlinpoet.ClassName
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.reflect.KClass

private const val PACKAGE_ROOT = "com.gabrieldrn.carbon.foundation.color"

private val themeAbstractionName = ClassName(PACKAGE_ROOT, "Theme")

private const val COMPONENT_INTERFACE_KDOC =
    "Color tokens for the %s component."

private const val COMPONENT_IMPLEMENTATION_KDOC =
    "Color tokens for the %s component in the %s theme."

private val themeDocs: Map<CarbonTheme, String> = mapOf(
    CarbonTheme.White to whiteThemeDoc,
    CarbonTheme.Gray10 to g10ThemeDoc,
    CarbonTheme.Gray90 to g90ThemeDoc,
    CarbonTheme.Gray100 to g100ThemeDoc,
)

/**
 * Describes one of Carbon's color components (`Theme`'s nested token sets), generated as its
 * own independent token set ahead of `Theme` so it can be referenced from it.
 */
private data class ComponentSpec(
    val kClass: KClass<*>,
    val componentName: String,
    val subpackage: String,
    val extractor: (Theme) -> Any,
)

private val components = listOf(
    ComponentSpec(AiColors::class, "AiColors", "ai") { it.aiColors },
    ComponentSpec(ButtonColors::class, "ButtonColors", "button") { it.buttonColors },
    ComponentSpec(ChatColors::class, "ChatColors", "chat") { it.chatColors },
    ComponentSpec(NotificationColors::class, "NotificationColors", "notification") {
        it.notificationColors
    },
    ComponentSpec(TagColors::class, "TagColors", "tag") { it.tagColors },
    ComponentSpec(SyntaxColors::class, "SyntaxColors", "syntax") { it.syntaxColors },
)

fun main(args: Array<String>) {
    val sourcePath: Path = Paths.get(args[0])

    val themes: Map<CarbonTheme, Theme> = deserializeColorTokens()

    val componentReferences: Map<KClass<*>, TokenSetResult> = components.associate { spec ->
        val result = generateTokenSetClass(
            abstractionClassName = ClassName(
                "$PACKAGE_ROOT.${spec.subpackage}",
                spec.componentName
            ),
            classKdoc = COMPONENT_INTERFACE_KDOC.format(spec.componentName.removeSuffix("Colors")),
            tokenSetData = themes.mapValues { (_, theme) -> spec.extractor(theme) },
            instanceName = { theme -> theme.name + spec.componentName },
            instanceKdoc = { theme ->
                COMPONENT_IMPLEMENTATION_KDOC.format(
                    spec.componentName.removeSuffix("Colors"),
                    theme.name
                )
            },
            sourcePath = sourcePath,
        )
        spec.kClass to result
    }

    generateTokenSetClass(
        abstractionClassName = themeAbstractionName,
        classKdoc = abstractThemeDoc,
        tokenSetData = themes,
        instanceName = { theme -> theme.name + "Theme" },
        instanceKdoc = { theme -> themeDocs[theme] },
        componentReferences = componentReferences,
        sourcePath = sourcePath,
    )
}
