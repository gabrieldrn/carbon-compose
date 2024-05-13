package carbon.android.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import carbon.android.foundation.SMALL_TOUCH_TARGET_SIZE_MESSAGE
import carbon.android.foundation.spacing.SpacingScale

public enum class ButtonSize(internal val height: Dp) {

    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("LargeProductive")
    )
    Small(height = 32.dp),

    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("LargeProductive")
    )
    Medium(height = 40.dp),
    LargeProductive(height = 48.dp),
    LargeExpressive(height = 48.dp),
    ExtraLarge(height = 64.dp),
    TwiceExtraLarge(height = 80.dp);

    internal companion object {
        val ButtonSize.isExtraLarge get() = this == ExtraLarge || this == TwiceExtraLarge

        @Suppress("DEPRECATION")
        fun ButtonSize.getContainerPaddings() = when (this) {
            Small -> PaddingValues(
                start = SpacingScale.spacing05,
                top = 7.dp,
                bottom = 7.dp
            )
            Medium -> PaddingValues(
                start = SpacingScale.spacing05,
                top = 11.dp,
                bottom = 11.dp
            )
            LargeProductive,
            LargeExpressive -> PaddingValues(
                start = SpacingScale.spacing05,
                top = 14.dp,
                bottom = 14.dp
            )
            ExtraLarge,
            TwiceExtraLarge -> PaddingValues(
                start = SpacingScale.spacing05,
                top = SpacingScale.spacing05,
                bottom = SpacingScale.spacing05
            )
        }
    }
}
