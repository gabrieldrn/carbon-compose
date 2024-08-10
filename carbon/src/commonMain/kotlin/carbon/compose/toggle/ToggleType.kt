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

package carbon.compose.toggle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
internal sealed class ToggleType(
    val width: Dp,
    val height: Dp,
    val handleSize: Dp,
) {
    data object Default : ToggleType(
        width = 48.dp,
        height = 24.dp,
        handleSize = 18.dp,
    )

    data object Small : ToggleType(
        width = 32.dp,
        height = 16.dp,
        handleSize = 10.dp,
    )
}
