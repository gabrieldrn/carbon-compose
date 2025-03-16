// ----------------------------------
// /!\ Generated code. Do not modify.
// ----------------------------------
package com.gabrieldrn.carbon.foundation.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.foundation.color.ai.AiColors
import com.gabrieldrn.carbon.foundation.color.button.ButtonColors
import com.gabrieldrn.carbon.foundation.color.chat.ChatColors
import com.gabrieldrn.carbon.foundation.color.notification.NotificationColors
import com.gabrieldrn.carbon.foundation.color.tag.TagColors
import kotlin.Suppress

/**
 * Themes are used to modify existing components to fit a specific visual style. By using Carbon’s
 * tokens, developers can easily customize all of their components by changing a set of universal
 * variables, eliminating the need to modify individual components.
 *
 * Themes serve as an organizational framework for color in Carbon, with each theme based on a
 * specific primary background color. And they actually get their names from their background color.
 *
 * See [Themes guidelines](https://carbondesignsystem.com/guidelines/themes/overview/) for more
 * information.
 */
@Immutable
@Suppress("UndocumentedPublicProperty")
public data class Theme(
    public val background: Color,
    public val backgroundActive: Color,
    public val backgroundBrand: Color,
    public val backgroundHover: Color,
    public val backgroundInverse: Color,
    public val backgroundInverseHover: Color,
    public val backgroundSelected: Color,
    public val backgroundSelectedHover: Color,
    public val borderDisabled: Color,
    public val borderInteractive: Color,
    public val borderInverse: Color,
    public val borderStrong01: Color,
    public val borderStrong02: Color,
    public val borderStrong03: Color,
    public val borderSubtle00: Color,
    public val borderSubtle01: Color,
    public val borderSubtle02: Color,
    public val borderSubtle03: Color,
    public val borderSubtleSelected01: Color,
    public val borderSubtleSelected02: Color,
    public val borderSubtleSelected03: Color,
    public val borderTile01: Color,
    public val borderTile02: Color,
    public val borderTile03: Color,
    public val field01: Color,
    public val field02: Color,
    public val field03: Color,
    public val fieldHover01: Color,
    public val fieldHover02: Color,
    public val fieldHover03: Color,
    public val focus: Color,
    public val focusInset: Color,
    public val focusInverse: Color,
    public val highlight: Color,
    public val iconDisabled: Color,
    public val iconInteractive: Color,
    public val iconInverse: Color,
    public val iconOnColor: Color,
    public val iconOnColorDisabled: Color,
    public val iconPrimary: Color,
    public val iconSecondary: Color,
    public val interactive: Color,
    public val layer01: Color,
    public val layer02: Color,
    public val layer03: Color,
    public val layerAccent01: Color,
    public val layerAccent02: Color,
    public val layerAccent03: Color,
    public val layerAccentActive01: Color,
    public val layerAccentActive02: Color,
    public val layerAccentActive03: Color,
    public val layerAccentHover01: Color,
    public val layerAccentHover02: Color,
    public val layerAccentHover03: Color,
    public val layerActive01: Color,
    public val layerActive02: Color,
    public val layerActive03: Color,
    public val layerHover01: Color,
    public val layerHover02: Color,
    public val layerHover03: Color,
    public val layerSelected01: Color,
    public val layerSelected02: Color,
    public val layerSelected03: Color,
    public val layerSelectedDisabled: Color,
    public val layerSelectedHover01: Color,
    public val layerSelectedHover02: Color,
    public val layerSelectedHover03: Color,
    public val layerSelectedInverse: Color,
    public val linkInverse: Color,
    public val linkInverseActive: Color,
    public val linkInverseHover: Color,
    public val linkInverseVisited: Color,
    public val linkPrimary: Color,
    public val linkPrimaryHover: Color,
    public val linkSecondary: Color,
    public val linkVisited: Color,
    public val overlay: Color,
    public val shadow: Color,
    public val skeletonBackground: Color,
    public val skeletonElement: Color,
    public val supportCautionMajor: Color,
    public val supportCautionMinor: Color,
    public val supportCautionUndefined: Color,
    public val supportError: Color,
    public val supportErrorInverse: Color,
    public val supportInfo: Color,
    public val supportInfoInverse: Color,
    public val supportSuccess: Color,
    public val supportSuccessInverse: Color,
    public val supportWarning: Color,
    public val supportWarningInverse: Color,
    public val textDisabled: Color,
    public val textError: Color,
    public val textHelper: Color,
    public val textInverse: Color,
    public val textOnColor: Color,
    public val textOnColorDisabled: Color,
    public val textPlaceholder: Color,
    public val textPrimary: Color,
    public val textSecondary: Color,
    public val toggleOff: Color,
    public val aiColors: AiColors,
    public val buttonColors: ButtonColors,
    public val chatColors: ChatColors,
    public val notificationColors: NotificationColors,
    public val tagColors: TagColors,
)
