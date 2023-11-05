package dev.gabrieldrn.carbon.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.spacing.SpacingScale

public enum class ButtonSize(internal val height: Dp) {

    Small(height = 32.dp),
    Medium(height = 40.dp),
    LargeProductive(height = 48.dp),
    LargeExpressive(height = 48.dp),
    ExtraLarge(height = 64.dp),
    TwiceExtraLarge(height = 80.dp);

    internal val isExtraLarge get() = this == ExtraLarge || this == TwiceExtraLarge

    internal fun getContainerPaddings() = when (this) {
        Small -> PaddingValues(start = SpacingScale.spacing05, top = 7.dp, bottom = 7.dp)
        Medium -> PaddingValues(start = SpacingScale.spacing05, top = 11.dp, bottom = 11.dp)
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
