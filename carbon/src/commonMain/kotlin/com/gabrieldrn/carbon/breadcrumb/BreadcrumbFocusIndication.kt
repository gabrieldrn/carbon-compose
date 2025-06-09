/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.breadcrumb

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.foundation.color.Theme
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

internal class BreadcrumbIndication(
    private val theme: Theme
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return object: Modifier.Node(), DrawModifierNode {
            private var isFocused by mutableStateOf(false)

            override fun ContentDrawScope.draw() {
                drawContent()

                if (isFocused) {
                    drawRect(
                        color = theme.focus,
                        style = Stroke(width = 1.dp.toPx())
                    )
                }
            }

            override fun onAttach() {
                coroutineScope.launch {
                    interactionSource.interactions.filterIsInstance<FocusInteraction>()
                        .collect { interaction ->
                            isFocused = interaction is FocusInteraction.Focus
                        }
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BreadcrumbIndication) return false

        if (theme != other.theme) return false

        return true
    }

    override fun hashCode(): Int = theme.hashCode()
}
