package carbon.compose.textinput

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.SMALL_TOUCH_TARGET_SIZE_MESSAGE

internal const val TEXT_INPUT_HEIGHT_SMALL_DP = 32
internal const val TEXT_INPUT_HEIGHT_MEDIUM_DP = 40
internal const val TEXT_INPUT_HEIGHT_LARGE_DP = 48

/**
 * Represents the size of a text input.
 */
@Stable
public enum class TextInputSize(internal val height: Dp) {

    /**
     * Use when space is constricted or when placing a text input in a form that is long and
     * complex.
     */
    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("Large")
    )
    Small(TEXT_INPUT_HEIGHT_SMALL_DP.dp),

    /**
     * This is the default size and the most commonly used size. When in doubt, use the medium size.
     */
    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("Large")
    )
    Medium(TEXT_INPUT_HEIGHT_MEDIUM_DP.dp),

    /**
     * Use when there is a lot of space to work with. The large size is typically used in simple
     * forms or when a text input is placed by itself on a page.
     */
    Large(TEXT_INPUT_HEIGHT_LARGE_DP.dp)
}
