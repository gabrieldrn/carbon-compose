package carbon.compose.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.SMALL_TOUCH_TARGET_SIZE_MESSAGE
import carbon.compose.foundation.spacing.SpacingScale

/**
 * Enum class representing different sizes for a button.
 * Each size has an associated height in dp.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 *
 * @property height The height of the button in dp.
 */
public sealed class ButtonSize(internal val height: Dp) {

    /**
     * Use when there is not enough vertical space for the default or field-sized button.
     */
    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("LargeProductive")
    )
    public data object Small : ButtonSize(height = 32.dp)

    /**
     * Use when buttons are paired with input fields.
     */
    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("LargeProductive")
    )
    public data object Medium : ButtonSize(height = 40.dp)

    /**
     * This is the most common button size.
     */
    public data object LargeProductive : ButtonSize(height = 48.dp)

    /**
     * The larger expressive type size within this button provides balance when used with 16sp body
     * copy. Used by the IBM.com team in website banners.
     */
    public data object LargeExpressive : ButtonSize(height = 48.dp)

    /**
     * Use when buttons bleed to the edge of a larger component, like side panels or modals.
     */
    public data object ExtraLarge : ButtonSize(height = 64.dp)

    /**
     * Use when buttons bleed to the edge of a larger component, like side panels or modals.
     */
    public data object TwiceExtraLarge : ButtonSize(height = 80.dp)

    internal companion object {
        val ButtonSize.isExtraLarge get() = this == ExtraLarge || this == TwiceExtraLarge

        @Suppress("DEPRECATION")
        val values: Array<ButtonSize> = arrayOf(
            Small,
            Medium,
            LargeProductive,
            LargeExpressive,
            ExtraLarge,
            TwiceExtraLarge
        )

        @Suppress("DEPRECATION")
        fun valueOf(value: String): ButtonSize {
            return when (value) {
                "Small" -> Small
                "Medium" -> Medium
                "LargeProductive" -> LargeProductive
                "LargeExpressive" -> LargeExpressive
                "ExtraLarge" -> ExtraLarge
                "TwiceExtraLarge" -> TwiceExtraLarge
                else -> throw IllegalArgumentException(
                    "No object carbon.compose.button.ButtonSize.$value"
                )
            }
        }

        @Suppress("DEPRECATION")
        internal fun ButtonSize.getContainerPaddings(): PaddingValues = when (this) {
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
