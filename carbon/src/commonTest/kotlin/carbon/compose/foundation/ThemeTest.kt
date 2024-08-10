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

package carbon.compose.foundation

import androidx.compose.ui.graphics.Color
import carbon.compose.foundation.color.WhiteTheme
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ThemeTest {

    @Test
    fun theme_copy_returnsNewInstanceWithSameValues() {
        val theme = WhiteTheme

        val newTheme = theme.copy()

        assertTrue(newTheme !== theme, "newTheme should not be the same instance as theme")
        assertEquals(theme, newTheme, "newTheme should have the same values as theme")
    }

    @Test
    fun theme_copyWithNewValues_returnsNewInstanceWithNewValues() {
        val theme = WhiteTheme

        val newColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

        val newTheme = theme.copy(
            layer01 = newColor
        )

        assertTrue(newTheme !== theme, "newTheme should not be the same instance as theme")
        assertNotEquals(theme, newTheme, "newTheme should have different values than theme")
        assertEquals(newColor, newTheme.layer01, "layer01 should equal to newColor")
    }
}
