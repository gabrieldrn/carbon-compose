package carbon.compose.dropdown.base

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.SMALL_TOUCH_TARGET_SIZE_MESSAGE

internal const val DROPDOWN_HEIGHT_SMALL_DP = 32
internal const val DROPDOWN_HEIGHT_MEDIUM_DP = 40
internal const val DROPDOWN_HEIGHT_LARGE_DP = 48

/**
 * Input height for dropdowns.
 */
public enum class DropdownSize(internal val height: Dp) {

    /**
     * Use when space is constricted or when placing a dropdown in a form that is long and complex.
     */
    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("Large")
    )
    Small(DROPDOWN_HEIGHT_SMALL_DP.dp),

    /**
     * This is the default size.
     */
    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("Large")
    )
    Medium(DROPDOWN_HEIGHT_MEDIUM_DP.dp),

    /**
     * Choose this size when there is a lot of space to work with. This size is typically used in
     * simple forms or when a dropdown is placed by itself on a page, for example as a filter.
     * This should be the default size for dropdowns on portable devices for accessibility reasons.
     */
    Large(DROPDOWN_HEIGHT_LARGE_DP.dp)
}
