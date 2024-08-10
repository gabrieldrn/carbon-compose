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

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import carbon.compose.CarbonDesignSystem

@Preview(showBackground = true)
@Composable
private fun DefaultTogglePreview() {
    CarbonDesignSystem {
        var isToggled by remember { mutableStateOf(false) }
        Toggle(
            isToggled = isToggled,
            onToggleChange = { isToggled = it },
            label = "Label",
            actionText = if (isToggled) "On" else "Off",
//            isEnabled = false,
//            isReadOnly = true,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmallTogglePreview() {
    CarbonDesignSystem {
        var isToggled by remember { mutableStateOf(false) }
        SmallToggle(
            isToggled = isToggled,
            onToggleChange = { isToggled = it },
            actionText = if (isToggled) "On" else "Off",
//            isEnabled = false,
//            isReadOnly = true,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
