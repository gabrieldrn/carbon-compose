package carbon.compose.textinput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.Theme

@Immutable
internal class TextInputColors private constructor(
    private val theme: Theme,
    private val layer: Layer
) {
    val placeholderTextColor: Color
        get() = theme.textPlaceholder

    val borderErrorColor = theme.supportError

    val backgroundColor: Color
        get() {
            return when (layer) {
                Layer.Layer00 -> theme.field01
                Layer.Layer01 -> theme.field02
                Layer.Layer02,
                Layer.Layer03 -> theme.field03
            }
        }

    val borderColor: Color
        get() = when (layer) {
            Layer.Layer00 -> theme.borderStrong01
            Layer.Layer01 -> theme.borderStrong02
            Layer.Layer02,
            Layer.Layer03 -> theme.borderStrong03
        }

    @Composable
    fun labelTextColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == TextInputState.Disabled) textDisabled
                else textSecondary
            }
        )

    @Composable
    fun fieldTextColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == TextInputState.Disabled) textDisabled
                else textPrimary
            }
        )

    @Composable
    fun helperTextColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == TextInputState.Error) textError
                else textPrimary
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TextInputColors) return false

        if (theme != other.theme) return false
        if (layer != other.layer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = theme.hashCode()
        result = 31 * result + layer.hashCode()
        return result
    }

    companion object {
        @Composable
        fun colors() = TextInputColors(Carbon.theme, Carbon.layer)
    }
}
