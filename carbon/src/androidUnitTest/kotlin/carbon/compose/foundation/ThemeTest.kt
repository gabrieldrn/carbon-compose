package carbon.compose.foundation

import androidx.compose.ui.graphics.Color
import carbon.compose.foundation.color.WhiteTheme
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ThemeTest {

    @Test
    fun theme_copy_returnsNewInstanceWithSameValues() {
        val theme = WhiteTheme

        val newTheme = theme.copy()

        assert(newTheme !== theme) {
            "newTheme should not be the same instance as theme"
        }
        assertEquals(theme, newTheme, "newTheme should have the same values as theme")
    }

    @Test
    fun theme_copyWithNewValues_returnsNewInstanceWithNewValues() {
        val theme = WhiteTheme

        val newColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

        val newTheme = theme.copy(
            layer01 = newColor
        )

        assert(newTheme !== theme) {
            "newTheme should not be the same instance as theme"
        }
        assertNotEquals(theme, newTheme, "newTheme should have different values than theme")
        assertEquals(newColor, newTheme.layer01, "layer01 should equal to newColor")
    }
}
