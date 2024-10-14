// ----------------------------------
// /!\ Generated code. Do not modify.
// ----------------------------------
package com.gabrieldrn.carbon.foundation.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.foundation.color.ai.AiColors
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
public abstract class Theme {
  /**
   * Default page background.
   * UI Shell base color.
   */
  public abstract val background: Color

  /**
   * Active color for [background].
   */
  public abstract val backgroundActive: Color

  /**
   * Feature background color.
   */
  public abstract val backgroundBrand: Color

  /**
   * Hover color for [background].
   * Hover color for transparent backgrounds.
   */
  public abstract val backgroundHover: Color

  /**
   * High contrast backgrounds.
   * High contrast elements.
   */
  public abstract val backgroundInverse: Color

  /**
   * Hover color for [backgroundInverse].
   */
  public abstract val backgroundInverseHover: Color

  /**
   * Selected color for [background].
   */
  public abstract val backgroundSelected: Color

  /**
   * Hover color for [backgroundSelected].
   */
  public abstract val backgroundSelectedHover: Color

  /**
   * Disabled border color (excluding border-subtles).
   */
  public abstract val borderDisabled: Color

  /**
   * 3:1 AA contrast.
   * Selected borders.
   * Active borders.
   */
  public abstract val borderInteractive: Color

  /**
   * High contrast border.
   * 4.5:1 AA non-text contrast.
   */
  public abstract val borderInverse: Color

  /**
   * Medium contrast border.
   * Border-bottom paired with [field01].
   * 3:1 AA non-text contrast.
   */
  public abstract val borderStrong01: Color

  /**
   * Medium contrast border.
   * Border-bottom paired with [field02].
   * 3:1 AA non-text contrast.
   */
  public abstract val borderStrong02: Color

  /**
   * Medium contrast border.
   * Border-bottom paired with [field03].
   * 3:1 AA non-text contrast.
   */
  public abstract val borderStrong03: Color

  /**
   * Subtle borders paired with [background].
   */
  public abstract val borderSubtle00: Color

  /**
   * Subtle borders paired with [layer01].
   */
  public abstract val borderSubtle01: Color

  /**
   * Subtle borders paired with [layer02].
   */
  public abstract val borderSubtle02: Color

  /**
   * Subtle borders paired with [layer03].
   */
  public abstract val borderSubtle03: Color

  /**
   * Selected color for [borderSubtle01].
   */
  public abstract val borderSubtleSelected01: Color

  /**
   * Selected color for [borderSubtle02].
   */
  public abstract val borderSubtleSelected02: Color

  /**
   * Selected color for [borderSubtle03].
   */
  public abstract val borderSubtleSelected03: Color

  /**
   * Operable tile indicator paired with [layer01].
   */
  public abstract val borderTile01: Color

  /**
   * Operable tile indicator paired with [layer02].
   */
  public abstract val borderTile02: Color

  /**
   * Operable tile indicator paired with [layer03].
   */
  public abstract val borderTile03: Color

  /**
   * Active color for [dangerPrimary].
   * Active color for [dangerSecondary].
   */
  public abstract val buttonDangerActive: Color

  /**
   * Hover color for [dangerPrimary].
   * Hover color for [dangerSecondary].
   */
  public abstract val buttonDangerHover: Color

  /**
   * Primary danger button color.
   * 3:1 AA non-text contrast.
   */
  public abstract val buttonDangerPrimary: Color

  /**
   * Tertiary danger button color.
   * Ghost danger button color.
   * 4.5:1 AA text contrast.
   */
  public abstract val buttonDangerSecondary: Color

  /**
   * Disabled color for button elements.
   */
  public abstract val buttonDisabled: Color

  /**
   * Primary button color.
   */
  public abstract val buttonPrimary: Color

  /**
   * Active color for [buttonPrimary].
   */
  public abstract val buttonPrimaryActive: Color

  /**
   * Hover color for [buttonPrimary].
   */
  public abstract val buttonPrimaryHover: Color

  /**
   * Secondary button color.
   */
  public abstract val buttonSecondary: Color

  /**
   * Active color for [buttonSecondary].
   */
  public abstract val buttonSecondaryActive: Color

  /**
   * Hover color for [buttonSecondary].
   */
  public abstract val buttonSecondaryHover: Color

  /**
   * Fluid button separator.
   * 3:1 AA non-text contrast.
   */
  public abstract val buttonSeparator: Color

  /**
   * Tertiary button color.
   * 4.5:1 AA text contrast.
   */
  public abstract val buttonTertiary: Color

  /**
   * Active color for [buttonTertiary].
   */
  public abstract val buttonTertiaryActive: Color

  /**
   * Hover color for [buttonTertiary].
   */
  public abstract val buttonTertiaryHover: Color

  /**
   * Default input fields.
   * Fields on [background].
   */
  public abstract val field01: Color

  /**
   * Secondary input fields.
   * Fields on [layer01].
   */
  public abstract val field02: Color

  /**
   * Tertiary input fields.
   * Fields on [layer02].
   */
  public abstract val field03: Color

  /**
   * Hover color for [field01].
   */
  public abstract val fieldHover01: Color

  /**
   * Hover color for [field02].
   */
  public abstract val fieldHover02: Color

  /**
   * Hover color for [field03].
   */
  public abstract val fieldHover03: Color

  /**
   * Focus border.
   * Focus underline.
   */
  public abstract val focus: Color

  /**
   * Contrast border paired with [focus].
   */
  public abstract val focusInset: Color

  /**
   * Focus on high contrast moments.
   */
  public abstract val focusInverse: Color

  /**
   * Disabled icon color.
   */
  public abstract val iconDisabled: Color

  /**
   * Icons that indicate operability.
   */
  public abstract val iconInteractive: Color

  /**
   * Inverse icon color.
   */
  public abstract val iconInverse: Color

  /**
   * Icons on interactive colors.
   * Icons on non-layer colors.
   */
  public abstract val iconOnColor: Color

  /**
   * Disabled color for [iconOnColor].
   */
  public abstract val iconOnColorDisabled: Color

  /**
   * Primary icons.
   */
  public abstract val iconPrimary: Color

  /**
   * Secondary icons.
   */
  public abstract val iconSecondary: Color

  /**
   * Tertiary background paired with [layer01].
   */
  public abstract val layerAccent01: Color

  /**
   * Tertiary background paired with [layer02].
   */
  public abstract val layerAccent02: Color

  /**
   * Tertiary background paired with [layer03].
   */
  public abstract val layerAccent03: Color

  /**
   * Active color for [layerAccent01].
   */
  public abstract val layerAccentActive01: Color

  /**
   * Active color for [layerAccent02].
   */
  public abstract val layerAccentActive02: Color

  /**
   * Active color for [layerAccent03].
   */
  public abstract val layerAccentActive03: Color

  /**
   * Hover color for [layerAccent01].
   */
  public abstract val layerAccentHover01: Color

  /**
   * Hover color for [layerAccent02].
   */
  public abstract val layerAccentHover02: Color

  /**
   * Hover color for [layerAccent03].
   */
  public abstract val layerAccentHover03: Color

  /**
   * Container color on [background].
   * Secondary page background.
   */
  public abstract val layer01: Color

  /**
   * Container color on [layer01].
   */
  public abstract val layer02: Color

  /**
   * Container color on [layer02].
   */
  public abstract val layer03: Color

  /**
   * Active color for [layer01].
   */
  public abstract val layerActive01: Color

  /**
   * Active color for [layer02].
   */
  public abstract val layerActive02: Color

  /**
   * Active color for [layer03].
   */
  public abstract val layerActive03: Color

  /**
   * Hover color for [layer01].
   */
  public abstract val layerHover01: Color

  /**
   * Hover color for [layer02].
   */
  public abstract val layerHover02: Color

  /**
   * Hover color for [layer03].
   */
  public abstract val layerHover03: Color

  /**
   * Selected color for [layer01].
   */
  public abstract val layerSelected01: Color

  /**
   * Selected color for [layer02].
   */
  public abstract val layerSelected02: Color

  /**
   * Selected color for [layer03].
   */
  public abstract val layerSelected03: Color

  /**
   * Disabled color for selected layers.
   */
  public abstract val layerSelectedDisabled: Color

  /**
   * Hover color for [layerSelected01].
   */
  public abstract val layerSelectedHover01: Color

  /**
   * Hover color for [layerSelected02].
   */
  public abstract val layerSelectedHover02: Color

  /**
   * Hover color for [layerSelected03].
   */
  public abstract val layerSelectedHover03: Color

  /**
   * High contrast elements.
   * 4.5:1 AA element contrast.
   */
  public abstract val layerSelectedInverse: Color

  /**
   * Links on [backgroundInverse] backgrounds.
   */
  public abstract val linkInverse: Color

  /**
   * Active color for links on [backgroundInverse] backgrounds.
   */
  public abstract val linkInverseActive: Color

  /**
   * Hover color for links on [backgroundInverse] backgrounds.
   */
  public abstract val linkInverseHover: Color

  /**
   * Color for visited links on [backgroundInverse] backgrounds.
   */
  public abstract val linkInverseVisited: Color

  /**
   * Primary links.
   */
  public abstract val linkPrimary: Color

  /**
   * Hover color for [linkPrimary].
   */
  public abstract val linkPrimaryHover: Color

  /**
   * Secondary link color for lower contrast backgrounds.
   */
  public abstract val linkSecondary: Color

  /**
   * Color for visited links.
   */
  public abstract val linkVisited: Color

  /**
   * Highlight color.
   */
  public abstract val highlight: Color

  /**
   * 3:1 AA contrast.
   * Selected elements.
   * Active elements.
   * Accent icons.
   */
  public abstract val interactive: Color

  /**
   * Background overlay.
   */
  public abstract val overlay: Color

  /**
   * Skeleton color for containers.
   */
  public abstract val skeletonBackground: Color

  /**
   * Skeleton color for text and UI elements.
   */
  public abstract val skeletonElement: Color

  /**
   * Off background.
   * 3:1 AA contrast.
   */
  public abstract val toggleOff: Color

  /**
   * Major caution status.
   */
  public abstract val supportCautionMajor: Color

  /**
   * Minor caution status.
   */
  public abstract val supportCautionMinor: Color

  /**
   * Undefined status.
   */
  public abstract val supportCautionUndefined: Color

  /**
   * Error.
   * Invalid state.
   */
  public abstract val supportError: Color

  /**
   * Error in high contrast moments.
   */
  public abstract val supportErrorInverse: Color

  /**
   * Information.
   */
  public abstract val supportInfo: Color

  /**
   * Information in high contrast moments.
   */
  public abstract val supportInfoInverse: Color

  /**
   * Success.
   * On.
   */
  public abstract val supportSuccess: Color

  /**
   * Success in high contrast moments.
   */
  public abstract val supportSuccessInverse: Color

  /**
   * Warning.
   */
  public abstract val supportWarning: Color

  /**
   * Warning in high contrast moments.
   */
  public abstract val supportWarningInverse: Color

  /**
   * Disabled text color.
   */
  public abstract val textDisabled: Color

  /**
   * Error message text.
   */
  public abstract val textError: Color

  /**
   * Tertiary text.
   * Help text.
   */
  public abstract val textHelper: Color

  /**
   * Inverse text color.
   */
  public abstract val textInverse: Color

  /**
   * Text on interactive colors.
   * Text on button colors.
   */
  public abstract val textOnColor: Color

  /**
   * Disabled color for [textOnColor].
   */
  public abstract val textOnColorDisabled: Color

  /**
   * Placeholder text.
   */
  public abstract val textPlaceholder: Color

  /**
   * Primary text.
   * Body copy.
   * Headers.
   * Hover text color for [textSecondary].
   */
  public abstract val textPrimary: Color

  /**
   * Secondary text.
   * Input labels.
   */
  public abstract val textSecondary: Color

  /**
   * Color tokens for the Ai component.
   */
  public abstract val aiColors: AiColors

  /**
   * Color tokens for the Tag component.
   */
  public abstract val tagColors: TagColors

  /**
   * Color tokens for the Chat component.
   */
  public abstract val chatColors: ChatColors

  /**
   * Color tokens for the Notification component.
   */
  public abstract val notificationColors: NotificationColors

  /**
   * Returns the container color based on a provided [layer].
   *
   * @param layer Associated layer. Defaults to layer 00.
   */
  public fun containerColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> background
    Layer.Layer01 -> layer01
    Layer.Layer02 -> layer02
    Layer.Layer03 -> layer03
  }

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
    buttonDangerActive: Color = this.buttonDangerActive,
    buttonDangerHover: Color = this.buttonDangerHover,
    buttonDangerPrimary: Color = this.buttonDangerPrimary,
    buttonDangerSecondary: Color = this.buttonDangerSecondary,
    buttonDisabled: Color = this.buttonDisabled,
    buttonPrimary: Color = this.buttonPrimary,
    buttonPrimaryActive: Color = this.buttonPrimaryActive,
    buttonPrimaryHover: Color = this.buttonPrimaryHover,
    buttonSecondary: Color = this.buttonSecondary,
    buttonSecondaryActive: Color = this.buttonSecondaryActive,
    buttonSecondaryHover: Color = this.buttonSecondaryHover,
    buttonSeparator: Color = this.buttonSeparator,
    buttonTertiary: Color = this.buttonTertiary,
    buttonTertiaryActive: Color = this.buttonTertiaryActive,
    buttonTertiaryHover: Color = this.buttonTertiaryHover,
    field01: Color = this.field01,
    field02: Color = this.field02,
    field03: Color = this.field03,
    fieldHover01: Color = this.fieldHover01,
    fieldHover02: Color = this.fieldHover02,
    fieldHover03: Color = this.fieldHover03,
    focus: Color = this.focus,
    focusInset: Color = this.focusInset,
    focusInverse: Color = this.focusInverse,
    iconDisabled: Color = this.iconDisabled,
    iconInteractive: Color = this.iconInteractive,
    iconInverse: Color = this.iconInverse,
    iconOnColor: Color = this.iconOnColor,
    iconOnColorDisabled: Color = this.iconOnColorDisabled,
    iconPrimary: Color = this.iconPrimary,
    iconSecondary: Color = this.iconSecondary,
    layerAccent01: Color = this.layerAccent01,
    layerAccent02: Color = this.layerAccent02,
    layerAccent03: Color = this.layerAccent03,
    layerAccentActive01: Color = this.layerAccentActive01,
    layerAccentActive02: Color = this.layerAccentActive02,
    layerAccentActive03: Color = this.layerAccentActive03,
    layerAccentHover01: Color = this.layerAccentHover01,
    layerAccentHover02: Color = this.layerAccentHover02,
    layerAccentHover03: Color = this.layerAccentHover03,
    layer01: Color = this.layer01,
    layer02: Color = this.layer02,
    layer03: Color = this.layer03,
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
    highlight: Color = this.highlight,
    interactive: Color = this.interactive,
    overlay: Color = this.overlay,
    skeletonBackground: Color = this.skeletonBackground,
    skeletonElement: Color = this.skeletonElement,
    toggleOff: Color = this.toggleOff,
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
    aiColors: AiColors = this.aiColors,
    tagColors: TagColors = this.tagColors,
    chatColors: ChatColors = this.chatColors,
    notificationColors: NotificationColors = this.notificationColors,
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
    override val buttonDangerActive: Color = buttonDangerActive
    override val buttonDangerHover: Color = buttonDangerHover
    override val buttonDangerPrimary: Color = buttonDangerPrimary
    override val buttonDangerSecondary: Color = buttonDangerSecondary
    override val buttonDisabled: Color = buttonDisabled
    override val buttonPrimary: Color = buttonPrimary
    override val buttonPrimaryActive: Color = buttonPrimaryActive
    override val buttonPrimaryHover: Color = buttonPrimaryHover
    override val buttonSecondary: Color = buttonSecondary
    override val buttonSecondaryActive: Color = buttonSecondaryActive
    override val buttonSecondaryHover: Color = buttonSecondaryHover
    override val buttonSeparator: Color = buttonSeparator
    override val buttonTertiary: Color = buttonTertiary
    override val buttonTertiaryActive: Color = buttonTertiaryActive
    override val buttonTertiaryHover: Color = buttonTertiaryHover
    override val field01: Color = field01
    override val field02: Color = field02
    override val field03: Color = field03
    override val fieldHover01: Color = fieldHover01
    override val fieldHover02: Color = fieldHover02
    override val fieldHover03: Color = fieldHover03
    override val focus: Color = focus
    override val focusInset: Color = focusInset
    override val focusInverse: Color = focusInverse
    override val iconDisabled: Color = iconDisabled
    override val iconInteractive: Color = iconInteractive
    override val iconInverse: Color = iconInverse
    override val iconOnColor: Color = iconOnColor
    override val iconOnColorDisabled: Color = iconOnColorDisabled
    override val iconPrimary: Color = iconPrimary
    override val iconSecondary: Color = iconSecondary
    override val layerAccent01: Color = layerAccent01
    override val layerAccent02: Color = layerAccent02
    override val layerAccent03: Color = layerAccent03
    override val layerAccentActive01: Color = layerAccentActive01
    override val layerAccentActive02: Color = layerAccentActive02
    override val layerAccentActive03: Color = layerAccentActive03
    override val layerAccentHover01: Color = layerAccentHover01
    override val layerAccentHover02: Color = layerAccentHover02
    override val layerAccentHover03: Color = layerAccentHover03
    override val layer01: Color = layer01
    override val layer02: Color = layer02
    override val layer03: Color = layer03
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
    override val highlight: Color = highlight
    override val interactive: Color = interactive
    override val overlay: Color = overlay
    override val skeletonBackground: Color = skeletonBackground
    override val skeletonElement: Color = skeletonElement
    override val toggleOff: Color = toggleOff
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
    override val aiColors: AiColors = aiColors
    override val tagColors: TagColors = tagColors
    override val chatColors: ChatColors = chatColors
    override val notificationColors: NotificationColors = notificationColors
  }
}
