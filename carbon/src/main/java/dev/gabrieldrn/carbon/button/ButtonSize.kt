package dev.gabrieldrn.carbon.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public enum class ButtonSize(internal val height: Dp) {

    Small(height = 32.dp),
    Medium(height = 40.dp),
    LargeProductive(height = 48.dp),
    LargeExpressive(height = 48.dp),
    ExtraLarge(height = 64.dp),
    TwiceExtraLarge(height = 80.dp);

    internal fun getLabelPaddings() = when (this) {
        Small -> PaddingValues(start = 16.dp, top = 7.dp, end = 64.dp, bottom = 7.dp)
        Medium -> PaddingValues(start = 16.dp, top = 11.dp, end = 64.dp, bottom = 11.dp)
        LargeProductive,
        LargeExpressive,
        ExtraLarge,
        TwiceExtraLarge -> PaddingValues(start = 16.dp, top = 14.dp, end = 64.dp, bottom = 14.dp)
    }
}
