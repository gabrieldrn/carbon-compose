package carbon.compose.textinput

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.SMALL_TOUCH_TARGET_SIZE_MESSAGE

internal const val TEXT_INPUT_HEIGHT_SMALL_DP = 32
internal const val TEXT_INPUT_HEIGHT_MEDIUM_DP = 40
internal const val TEXT_INPUT_HEIGHT_LARGE_DP = 48

@Stable
public enum class TextInputSize(internal val height: Dp) {

    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("Large")
    )
    Small(TEXT_INPUT_HEIGHT_SMALL_DP.dp),

    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("Large")
    )
    Medium(TEXT_INPUT_HEIGHT_MEDIUM_DP.dp),

    Large(TEXT_INPUT_HEIGHT_LARGE_DP.dp)
}
