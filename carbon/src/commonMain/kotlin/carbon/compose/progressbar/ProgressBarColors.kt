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

package carbon.compose.progressbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme

@Immutable
internal class ProgressBarColors private constructor(private val theme: Theme) {

    @Composable
    fun trackColor(state: ProgressBarState): State<Color> =
        rememberUpdatedState(
            when (state) {
                ProgressBarState.Active -> theme.borderInteractive
                ProgressBarState.Success -> theme.supportSuccess
                ProgressBarState.Error -> theme.supportError
            }
        )

    @Composable
    fun iconColor(state: ProgressBarState): State<Color> =
        rememberUpdatedState(
            when (state) {
                ProgressBarState.Active -> Color.Unspecified
                ProgressBarState.Success -> theme.supportSuccess
                ProgressBarState.Error -> theme.supportError
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProgressBarColors) return false

        return theme == other.theme
    }

    override fun hashCode(): Int = theme.hashCode()

    companion object {

        @Composable
        internal fun colors() = ProgressBarColors(Carbon.theme)
    }
}
