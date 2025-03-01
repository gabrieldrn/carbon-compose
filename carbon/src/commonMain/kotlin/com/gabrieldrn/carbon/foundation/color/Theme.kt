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
public abstract class Theme {
    public abstract val background: Color

    public abstract val backgroundActive: Color

    public abstract val backgroundBrand: Color

    public abstract val backgroundHover: Color

    public abstract val backgroundInverse: Color

    public abstract val backgroundInverseHover: Color

    public abstract val backgroundSelected: Color

    public abstract val backgroundSelectedHover: Color

    public abstract val borderDisabled: Color

    public abstract val borderInteractive: Color

    public abstract val borderInverse: Color

    public abstract val borderStrong01: Color

    public abstract val borderStrong02: Color

    public abstract val borderStrong03: Color

    public abstract val borderSubtle00: Color

    public abstract val borderSubtle01: Color

    public abstract val borderSubtle02: Color

    public abstract val borderSubtle03: Color

    public abstract val borderSubtleSelected01: Color

    public abstract val borderSubtleSelected02: Color

    public abstract val borderSubtleSelected03: Color

    public abstract val borderTile01: Color

    public abstract val borderTile02: Color

    public abstract val borderTile03: Color

    public abstract val field01: Color

    public abstract val field02: Color

    public abstract val field03: Color

    public abstract val fieldHover01: Color

    public abstract val fieldHover02: Color

    public abstract val fieldHover03: Color

    public abstract val focus: Color

    public abstract val focusInset: Color

    public abstract val focusInverse: Color

    public abstract val highlight: Color

    public abstract val iconDisabled: Color

    public abstract val iconInteractive: Color

    public abstract val iconInverse: Color

    public abstract val iconOnColor: Color

    public abstract val iconOnColorDisabled: Color

    public abstract val iconPrimary: Color

    public abstract val iconSecondary: Color

    public abstract val interactive: Color

    public abstract val layer01: Color

    public abstract val layer02: Color

    public abstract val layer03: Color

    public abstract val layerAccent01: Color

    public abstract val layerAccent02: Color

    public abstract val layerAccent03: Color

    public abstract val layerAccentActive01: Color

    public abstract val layerAccentActive02: Color

    public abstract val layerAccentActive03: Color

    public abstract val layerAccentHover01: Color

    public abstract val layerAccentHover02: Color

    public abstract val layerAccentHover03: Color

    public abstract val layerActive01: Color

    public abstract val layerActive02: Color

    public abstract val layerActive03: Color

    public abstract val layerHover01: Color

    public abstract val layerHover02: Color

    public abstract val layerHover03: Color

    public abstract val layerSelected01: Color

    public abstract val layerSelected02: Color

    public abstract val layerSelected03: Color

    public abstract val layerSelectedDisabled: Color

    public abstract val layerSelectedHover01: Color

    public abstract val layerSelectedHover02: Color

    public abstract val layerSelectedHover03: Color

    public abstract val layerSelectedInverse: Color

    public abstract val linkInverse: Color

    public abstract val linkInverseActive: Color

    public abstract val linkInverseHover: Color

    public abstract val linkInverseVisited: Color

    public abstract val linkPrimary: Color

    public abstract val linkPrimaryHover: Color

    public abstract val linkSecondary: Color

    public abstract val linkVisited: Color

    public abstract val overlay: Color

    public abstract val shadow: Color

    public abstract val skeletonBackground: Color

    public abstract val skeletonElement: Color

    public abstract val supportCautionMajor: Color

    public abstract val supportCautionMinor: Color

    public abstract val supportCautionUndefined: Color

    public abstract val supportError: Color

    public abstract val supportErrorInverse: Color

    public abstract val supportInfo: Color

    public abstract val supportInfoInverse: Color

    public abstract val supportSuccess: Color

    public abstract val supportSuccessInverse: Color

    public abstract val supportWarning: Color

    public abstract val supportWarningInverse: Color

    public abstract val textDisabled: Color

    public abstract val textError: Color

    public abstract val textHelper: Color

    public abstract val textInverse: Color

    public abstract val textOnColor: Color

    public abstract val textOnColorDisabled: Color

    public abstract val textPlaceholder: Color

    public abstract val textPrimary: Color

    public abstract val textSecondary: Color

    public abstract val toggleOff: Color

    public abstract val aiColors: AiColors

    public abstract val buttonColors: ButtonColors

    public abstract val chatColors: ChatColors

    public abstract val notificationColors: NotificationColors

    public abstract val tagColors: TagColors

    @Suppress("LongMethod")
    internal fun copy(
        background: Color = this.background,
        backgroundActive: Color = this.backgroundActive,
        backgroundBrand: Color = this.backgroundBrand,
        backgroundHover: Color = this.backgroundHover,
        backgroundInverse: Color = this.backgroundInverse,
        backgroundInverseHover: Color = this.backgroundInverseHover,
        backgroundSelected: Color = this.backgroundSelected,
        backgroundSelectedHover: Color = this.backgroundSelectedHover,
        borderDisabled: Color = this.borderDisabled,
        borderInteractive: Color = this.borderInteractive,
        borderInverse: Color = this.borderInverse,
        borderStrong01: Color = this.borderStrong01,
        borderStrong02: Color = this.borderStrong02,
        borderStrong03: Color = this.borderStrong03,
        borderSubtle00: Color = this.borderSubtle00,
        borderSubtle01: Color = this.borderSubtle01,
        borderSubtle02: Color = this.borderSubtle02,
        borderSubtle03: Color = this.borderSubtle03,
        borderSubtleSelected01: Color = this.borderSubtleSelected01,
        borderSubtleSelected02: Color = this.borderSubtleSelected02,
        borderSubtleSelected03: Color = this.borderSubtleSelected03,
        borderTile01: Color = this.borderTile01,
        borderTile02: Color = this.borderTile02,
        borderTile03: Color = this.borderTile03,
        field01: Color = this.field01,
        field02: Color = this.field02,
        field03: Color = this.field03,
        fieldHover01: Color = this.fieldHover01,
        fieldHover02: Color = this.fieldHover02,
        fieldHover03: Color = this.fieldHover03,
        focus: Color = this.focus,
        focusInset: Color = this.focusInset,
        focusInverse: Color = this.focusInverse,
        highlight: Color = this.highlight,
        iconDisabled: Color = this.iconDisabled,
        iconInteractive: Color = this.iconInteractive,
        iconInverse: Color = this.iconInverse,
        iconOnColor: Color = this.iconOnColor,
        iconOnColorDisabled: Color = this.iconOnColorDisabled,
        iconPrimary: Color = this.iconPrimary,
        iconSecondary: Color = this.iconSecondary,
        interactive: Color = this.interactive,
        layer01: Color = this.layer01,
        layer02: Color = this.layer02,
        layer03: Color = this.layer03,
        layerAccent01: Color = this.layerAccent01,
        layerAccent02: Color = this.layerAccent02,
        layerAccent03: Color = this.layerAccent03,
        layerAccentActive01: Color = this.layerAccentActive01,
        layerAccentActive02: Color = this.layerAccentActive02,
        layerAccentActive03: Color = this.layerAccentActive03,
        layerAccentHover01: Color = this.layerAccentHover01,
        layerAccentHover02: Color = this.layerAccentHover02,
        layerAccentHover03: Color = this.layerAccentHover03,
        layerActive01: Color = this.layerActive01,
        layerActive02: Color = this.layerActive02,
        layerActive03: Color = this.layerActive03,
        layerHover01: Color = this.layerHover01,
        layerHover02: Color = this.layerHover02,
        layerHover03: Color = this.layerHover03,
        layerSelected01: Color = this.layerSelected01,
        layerSelected02: Color = this.layerSelected02,
        layerSelected03: Color = this.layerSelected03,
        layerSelectedDisabled: Color = this.layerSelectedDisabled,
        layerSelectedHover01: Color = this.layerSelectedHover01,
        layerSelectedHover02: Color = this.layerSelectedHover02,
        layerSelectedHover03: Color = this.layerSelectedHover03,
        layerSelectedInverse: Color = this.layerSelectedInverse,
        linkInverse: Color = this.linkInverse,
        linkInverseActive: Color = this.linkInverseActive,
        linkInverseHover: Color = this.linkInverseHover,
        linkInverseVisited: Color = this.linkInverseVisited,
        linkPrimary: Color = this.linkPrimary,
        linkPrimaryHover: Color = this.linkPrimaryHover,
        linkSecondary: Color = this.linkSecondary,
        linkVisited: Color = this.linkVisited,
        overlay: Color = this.overlay,
        shadow: Color = this.shadow,
        skeletonBackground: Color = this.skeletonBackground,
        skeletonElement: Color = this.skeletonElement,
        supportCautionMajor: Color = this.supportCautionMajor,
        supportCautionMinor: Color = this.supportCautionMinor,
        supportCautionUndefined: Color = this.supportCautionUndefined,
        supportError: Color = this.supportError,
        supportErrorInverse: Color = this.supportErrorInverse,
        supportInfo: Color = this.supportInfo,
        supportInfoInverse: Color = this.supportInfoInverse,
        supportSuccess: Color = this.supportSuccess,
        supportSuccessInverse: Color = this.supportSuccessInverse,
        supportWarning: Color = this.supportWarning,
        supportWarningInverse: Color = this.supportWarningInverse,
        textDisabled: Color = this.textDisabled,
        textError: Color = this.textError,
        textHelper: Color = this.textHelper,
        textInverse: Color = this.textInverse,
        textOnColor: Color = this.textOnColor,
        textOnColorDisabled: Color = this.textOnColorDisabled,
        textPlaceholder: Color = this.textPlaceholder,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        toggleOff: Color = this.toggleOff,
        aiColors: AiColors = this.aiColors,
        buttonColors: ButtonColors = this.buttonColors,
        chatColors: ChatColors = this.chatColors,
        notificationColors: NotificationColors = this.notificationColors,
        tagColors: TagColors = this.tagColors,
    ): Theme = object : Theme() {
        override val background: Color = background

        override val backgroundActive: Color = backgroundActive

        override val backgroundBrand: Color = backgroundBrand

        override val backgroundHover: Color = backgroundHover

        override val backgroundInverse: Color = backgroundInverse

        override val backgroundInverseHover: Color = backgroundInverseHover

        override val backgroundSelected: Color = backgroundSelected

        override val backgroundSelectedHover: Color = backgroundSelectedHover

        override val borderDisabled: Color = borderDisabled

        override val borderInteractive: Color = borderInteractive

        override val borderInverse: Color = borderInverse

        override val borderStrong01: Color = borderStrong01

        override val borderStrong02: Color = borderStrong02

        override val borderStrong03: Color = borderStrong03

        override val borderSubtle00: Color = borderSubtle00

        override val borderSubtle01: Color = borderSubtle01

        override val borderSubtle02: Color = borderSubtle02

        override val borderSubtle03: Color = borderSubtle03

        override val borderSubtleSelected01: Color = borderSubtleSelected01

        override val borderSubtleSelected02: Color = borderSubtleSelected02

        override val borderSubtleSelected03: Color = borderSubtleSelected03

        override val borderTile01: Color = borderTile01

        override val borderTile02: Color = borderTile02

        override val borderTile03: Color = borderTile03

        override val field01: Color = field01

        override val field02: Color = field02

        override val field03: Color = field03

        override val fieldHover01: Color = fieldHover01

        override val fieldHover02: Color = fieldHover02

        override val fieldHover03: Color = fieldHover03

        override val focus: Color = focus

        override val focusInset: Color = focusInset

        override val focusInverse: Color = focusInverse

        override val highlight: Color = highlight

        override val iconDisabled: Color = iconDisabled

        override val iconInteractive: Color = iconInteractive

        override val iconInverse: Color = iconInverse

        override val iconOnColor: Color = iconOnColor

        override val iconOnColorDisabled: Color = iconOnColorDisabled

        override val iconPrimary: Color = iconPrimary

        override val iconSecondary: Color = iconSecondary

        override val interactive: Color = interactive

        override val layer01: Color = layer01

        override val layer02: Color = layer02

        override val layer03: Color = layer03

        override val layerAccent01: Color = layerAccent01

        override val layerAccent02: Color = layerAccent02

        override val layerAccent03: Color = layerAccent03

        override val layerAccentActive01: Color = layerAccentActive01

        override val layerAccentActive02: Color = layerAccentActive02

        override val layerAccentActive03: Color = layerAccentActive03

        override val layerAccentHover01: Color = layerAccentHover01

        override val layerAccentHover02: Color = layerAccentHover02

        override val layerAccentHover03: Color = layerAccentHover03

        override val layerActive01: Color = layerActive01

        override val layerActive02: Color = layerActive02

        override val layerActive03: Color = layerActive03

        override val layerHover01: Color = layerHover01

        override val layerHover02: Color = layerHover02

        override val layerHover03: Color = layerHover03

        override val layerSelected01: Color = layerSelected01

        override val layerSelected02: Color = layerSelected02

        override val layerSelected03: Color = layerSelected03

        override val layerSelectedDisabled: Color = layerSelectedDisabled

        override val layerSelectedHover01: Color = layerSelectedHover01

        override val layerSelectedHover02: Color = layerSelectedHover02

        override val layerSelectedHover03: Color = layerSelectedHover03

        override val layerSelectedInverse: Color = layerSelectedInverse

        override val linkInverse: Color = linkInverse

        override val linkInverseActive: Color = linkInverseActive

        override val linkInverseHover: Color = linkInverseHover

        override val linkInverseVisited: Color = linkInverseVisited

        override val linkPrimary: Color = linkPrimary

        override val linkPrimaryHover: Color = linkPrimaryHover

        override val linkSecondary: Color = linkSecondary

        override val linkVisited: Color = linkVisited

        override val overlay: Color = overlay

        override val shadow: Color = shadow

        override val skeletonBackground: Color = skeletonBackground

        override val skeletonElement: Color = skeletonElement

        override val supportCautionMajor: Color = supportCautionMajor

        override val supportCautionMinor: Color = supportCautionMinor

        override val supportCautionUndefined: Color = supportCautionUndefined

        override val supportError: Color = supportError

        override val supportErrorInverse: Color = supportErrorInverse

        override val supportInfo: Color = supportInfo

        override val supportInfoInverse: Color = supportInfoInverse

        override val supportSuccess: Color = supportSuccess

        override val supportSuccessInverse: Color = supportSuccessInverse

        override val supportWarning: Color = supportWarning

        override val supportWarningInverse: Color = supportWarningInverse

        override val textDisabled: Color = textDisabled

        override val textError: Color = textError

        override val textHelper: Color = textHelper

        override val textInverse: Color = textInverse

        override val textOnColor: Color = textOnColor

        override val textOnColorDisabled: Color = textOnColorDisabled

        override val textPlaceholder: Color = textPlaceholder

        override val textPrimary: Color = textPrimary

        override val textSecondary: Color = textSecondary

        override val toggleOff: Color = toggleOff

        override val aiColors: AiColors = aiColors

        override val buttonColors: ButtonColors = buttonColors

        override val chatColors: ChatColors = chatColors

        override val notificationColors: NotificationColors = notificationColors

        override val tagColors: TagColors = tagColors
    }

    @Suppress(
        "CognitiveComplexMethod",
        "CyclomaticComplexMethod",
        "LongMethod",
    )
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Theme) return false
        if (background != other.background) return false
        if (backgroundActive != other.backgroundActive) return false
        if (backgroundBrand != other.backgroundBrand) return false
        if (backgroundHover != other.backgroundHover) return false
        if (backgroundInverse != other.backgroundInverse) return false
        if (backgroundInverseHover != other.backgroundInverseHover) return false
        if (backgroundSelected != other.backgroundSelected) return false
        if (backgroundSelectedHover != other.backgroundSelectedHover) return false
        if (borderDisabled != other.borderDisabled) return false
        if (borderInteractive != other.borderInteractive) return false
        if (borderInverse != other.borderInverse) return false
        if (borderStrong01 != other.borderStrong01) return false
        if (borderStrong02 != other.borderStrong02) return false
        if (borderStrong03 != other.borderStrong03) return false
        if (borderSubtle00 != other.borderSubtle00) return false
        if (borderSubtle01 != other.borderSubtle01) return false
        if (borderSubtle02 != other.borderSubtle02) return false
        if (borderSubtle03 != other.borderSubtle03) return false
        if (borderSubtleSelected01 != other.borderSubtleSelected01) return false
        if (borderSubtleSelected02 != other.borderSubtleSelected02) return false
        if (borderSubtleSelected03 != other.borderSubtleSelected03) return false
        if (borderTile01 != other.borderTile01) return false
        if (borderTile02 != other.borderTile02) return false
        if (borderTile03 != other.borderTile03) return false
        if (field01 != other.field01) return false
        if (field02 != other.field02) return false
        if (field03 != other.field03) return false
        if (fieldHover01 != other.fieldHover01) return false
        if (fieldHover02 != other.fieldHover02) return false
        if (fieldHover03 != other.fieldHover03) return false
        if (focus != other.focus) return false
        if (focusInset != other.focusInset) return false
        if (focusInverse != other.focusInverse) return false
        if (highlight != other.highlight) return false
        if (iconDisabled != other.iconDisabled) return false
        if (iconInteractive != other.iconInteractive) return false
        if (iconInverse != other.iconInverse) return false
        if (iconOnColor != other.iconOnColor) return false
        if (iconOnColorDisabled != other.iconOnColorDisabled) return false
        if (iconPrimary != other.iconPrimary) return false
        if (iconSecondary != other.iconSecondary) return false
        if (interactive != other.interactive) return false
        if (layer01 != other.layer01) return false
        if (layer02 != other.layer02) return false
        if (layer03 != other.layer03) return false
        if (layerAccent01 != other.layerAccent01) return false
        if (layerAccent02 != other.layerAccent02) return false
        if (layerAccent03 != other.layerAccent03) return false
        if (layerAccentActive01 != other.layerAccentActive01) return false
        if (layerAccentActive02 != other.layerAccentActive02) return false
        if (layerAccentActive03 != other.layerAccentActive03) return false
        if (layerAccentHover01 != other.layerAccentHover01) return false
        if (layerAccentHover02 != other.layerAccentHover02) return false
        if (layerAccentHover03 != other.layerAccentHover03) return false
        if (layerActive01 != other.layerActive01) return false
        if (layerActive02 != other.layerActive02) return false
        if (layerActive03 != other.layerActive03) return false
        if (layerHover01 != other.layerHover01) return false
        if (layerHover02 != other.layerHover02) return false
        if (layerHover03 != other.layerHover03) return false
        if (layerSelected01 != other.layerSelected01) return false
        if (layerSelected02 != other.layerSelected02) return false
        if (layerSelected03 != other.layerSelected03) return false
        if (layerSelectedDisabled != other.layerSelectedDisabled) return false
        if (layerSelectedHover01 != other.layerSelectedHover01) return false
        if (layerSelectedHover02 != other.layerSelectedHover02) return false
        if (layerSelectedHover03 != other.layerSelectedHover03) return false
        if (layerSelectedInverse != other.layerSelectedInverse) return false
        if (linkInverse != other.linkInverse) return false
        if (linkInverseActive != other.linkInverseActive) return false
        if (linkInverseHover != other.linkInverseHover) return false
        if (linkInverseVisited != other.linkInverseVisited) return false
        if (linkPrimary != other.linkPrimary) return false
        if (linkPrimaryHover != other.linkPrimaryHover) return false
        if (linkSecondary != other.linkSecondary) return false
        if (linkVisited != other.linkVisited) return false
        if (overlay != other.overlay) return false
        if (shadow != other.shadow) return false
        if (skeletonBackground != other.skeletonBackground) return false
        if (skeletonElement != other.skeletonElement) return false
        if (supportCautionMajor != other.supportCautionMajor) return false
        if (supportCautionMinor != other.supportCautionMinor) return false
        if (supportCautionUndefined != other.supportCautionUndefined) return false
        if (supportError != other.supportError) return false
        if (supportErrorInverse != other.supportErrorInverse) return false
        if (supportInfo != other.supportInfo) return false
        if (supportInfoInverse != other.supportInfoInverse) return false
        if (supportSuccess != other.supportSuccess) return false
        if (supportSuccessInverse != other.supportSuccessInverse) return false
        if (supportWarning != other.supportWarning) return false
        if (supportWarningInverse != other.supportWarningInverse) return false
        if (textDisabled != other.textDisabled) return false
        if (textError != other.textError) return false
        if (textHelper != other.textHelper) return false
        if (textInverse != other.textInverse) return false
        if (textOnColor != other.textOnColor) return false
        if (textOnColorDisabled != other.textOnColorDisabled) return false
        if (textPlaceholder != other.textPlaceholder) return false
        if (textPrimary != other.textPrimary) return false
        if (textSecondary != other.textSecondary) return false
        if (toggleOff != other.toggleOff) return false
        if (aiColors != other.aiColors) return false
        if (buttonColors != other.buttonColors) return false
        if (chatColors != other.chatColors) return false
        if (notificationColors != other.notificationColors) return false
        if (tagColors != other.tagColors) return false
        return true
    }

    @Suppress("LongMethod")
    override fun hashCode(): Int {
        var result = background.hashCode()
        result = 31 * result + backgroundActive.hashCode()
        result = 31 * result + backgroundBrand.hashCode()
        result = 31 * result + backgroundHover.hashCode()
        result = 31 * result + backgroundInverse.hashCode()
        result = 31 * result + backgroundInverseHover.hashCode()
        result = 31 * result + backgroundSelected.hashCode()
        result = 31 * result + backgroundSelectedHover.hashCode()
        result = 31 * result + borderDisabled.hashCode()
        result = 31 * result + borderInteractive.hashCode()
        result = 31 * result + borderInverse.hashCode()
        result = 31 * result + borderStrong01.hashCode()
        result = 31 * result + borderStrong02.hashCode()
        result = 31 * result + borderStrong03.hashCode()
        result = 31 * result + borderSubtle00.hashCode()
        result = 31 * result + borderSubtle01.hashCode()
        result = 31 * result + borderSubtle02.hashCode()
        result = 31 * result + borderSubtle03.hashCode()
        result = 31 * result + borderSubtleSelected01.hashCode()
        result = 31 * result + borderSubtleSelected02.hashCode()
        result = 31 * result + borderSubtleSelected03.hashCode()
        result = 31 * result + borderTile01.hashCode()
        result = 31 * result + borderTile02.hashCode()
        result = 31 * result + borderTile03.hashCode()
        result = 31 * result + field01.hashCode()
        result = 31 * result + field02.hashCode()
        result = 31 * result + field03.hashCode()
        result = 31 * result + fieldHover01.hashCode()
        result = 31 * result + fieldHover02.hashCode()
        result = 31 * result + fieldHover03.hashCode()
        result = 31 * result + focus.hashCode()
        result = 31 * result + focusInset.hashCode()
        result = 31 * result + focusInverse.hashCode()
        result = 31 * result + highlight.hashCode()
        result = 31 * result + iconDisabled.hashCode()
        result = 31 * result + iconInteractive.hashCode()
        result = 31 * result + iconInverse.hashCode()
        result = 31 * result + iconOnColor.hashCode()
        result = 31 * result + iconOnColorDisabled.hashCode()
        result = 31 * result + iconPrimary.hashCode()
        result = 31 * result + iconSecondary.hashCode()
        result = 31 * result + interactive.hashCode()
        result = 31 * result + layer01.hashCode()
        result = 31 * result + layer02.hashCode()
        result = 31 * result + layer03.hashCode()
        result = 31 * result + layerAccent01.hashCode()
        result = 31 * result + layerAccent02.hashCode()
        result = 31 * result + layerAccent03.hashCode()
        result = 31 * result + layerAccentActive01.hashCode()
        result = 31 * result + layerAccentActive02.hashCode()
        result = 31 * result + layerAccentActive03.hashCode()
        result = 31 * result + layerAccentHover01.hashCode()
        result = 31 * result + layerAccentHover02.hashCode()
        result = 31 * result + layerAccentHover03.hashCode()
        result = 31 * result + layerActive01.hashCode()
        result = 31 * result + layerActive02.hashCode()
        result = 31 * result + layerActive03.hashCode()
        result = 31 * result + layerHover01.hashCode()
        result = 31 * result + layerHover02.hashCode()
        result = 31 * result + layerHover03.hashCode()
        result = 31 * result + layerSelected01.hashCode()
        result = 31 * result + layerSelected02.hashCode()
        result = 31 * result + layerSelected03.hashCode()
        result = 31 * result + layerSelectedDisabled.hashCode()
        result = 31 * result + layerSelectedHover01.hashCode()
        result = 31 * result + layerSelectedHover02.hashCode()
        result = 31 * result + layerSelectedHover03.hashCode()
        result = 31 * result + layerSelectedInverse.hashCode()
        result = 31 * result + linkInverse.hashCode()
        result = 31 * result + linkInverseActive.hashCode()
        result = 31 * result + linkInverseHover.hashCode()
        result = 31 * result + linkInverseVisited.hashCode()
        result = 31 * result + linkPrimary.hashCode()
        result = 31 * result + linkPrimaryHover.hashCode()
        result = 31 * result + linkSecondary.hashCode()
        result = 31 * result + linkVisited.hashCode()
        result = 31 * result + overlay.hashCode()
        result = 31 * result + shadow.hashCode()
        result = 31 * result + skeletonBackground.hashCode()
        result = 31 * result + skeletonElement.hashCode()
        result = 31 * result + supportCautionMajor.hashCode()
        result = 31 * result + supportCautionMinor.hashCode()
        result = 31 * result + supportCautionUndefined.hashCode()
        result = 31 * result + supportError.hashCode()
        result = 31 * result + supportErrorInverse.hashCode()
        result = 31 * result + supportInfo.hashCode()
        result = 31 * result + supportInfoInverse.hashCode()
        result = 31 * result + supportSuccess.hashCode()
        result = 31 * result + supportSuccessInverse.hashCode()
        result = 31 * result + supportWarning.hashCode()
        result = 31 * result + supportWarningInverse.hashCode()
        result = 31 * result + textDisabled.hashCode()
        result = 31 * result + textError.hashCode()
        result = 31 * result + textHelper.hashCode()
        result = 31 * result + textInverse.hashCode()
        result = 31 * result + textOnColor.hashCode()
        result = 31 * result + textOnColorDisabled.hashCode()
        result = 31 * result + textPlaceholder.hashCode()
        result = 31 * result + textPrimary.hashCode()
        result = 31 * result + textSecondary.hashCode()
        result = 31 * result + toggleOff.hashCode()
        result = 31 * result + aiColors.hashCode()
        result = 31 * result + buttonColors.hashCode()
        result = 31 * result + chatColors.hashCode()
        result = 31 * result + notificationColors.hashCode()
        result = 31 * result + tagColors.hashCode()
        return result
    }
}
