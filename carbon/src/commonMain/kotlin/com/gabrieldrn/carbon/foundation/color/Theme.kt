// Generated code. Do not modify.
package com.gabrieldrn.carbon.foundation.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
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
   * Linear gradient start value for all AI layers.
   */
  public abstract val aiAuraEnd: Color

  /**
   * Hover background color for AI layers.
   */
  public abstract val aiAuraHoverBackground: Color

  /**
   * Linear gradient end value for the AI aura hover.
   */
  public abstract val aiAuraHoverEnd: Color

  /**
   * Linear gradient start value for the AI aura hover.
   */
  public abstract val aiAuraHoverStart: Color

  /**
   * Linear gradient start value for large AI layers.
   */
  public abstract val aiAuraStart: Color

  /**
   * Linear gradient start value for small AI layers.
   */
  public abstract val aiAuraStartSm: Color

  /**
   * Linear gradient end value for AI borders.
   */
  public abstract val aiBorderEnd: Color

  /**
   * Linear gradient start value for AI borders.
   */
  public abstract val aiBorderStart: Color

  /**
   * Medium contrast border for AI elements.
   * Border-bottom paired with AI fields.
   * 3:1 AA non-text contrast.
   */
  public abstract val aiBorderStrong: Color

  /**
   * Drop shadow for the AI layer.
   */
  public abstract val aiDropShadow: Color

  /**
   * Inner shadow for the AI layer.
   */
  public abstract val aiInnerShadow: Color

  /**
   * Background overlay for AI components.
   */
  public abstract val aiOverlay: Color

  /**
   * Background color for the AI explainability popover.
   */
  public abstract val aiPopoverBackground: Color

  /**
   * 1 of 2 shadow colors for the AI explainability popover.
   */
  public abstract val aiPopoverShadowOuter01: Color

  /**
   * 2 of 2 shadow colors for the AI explainability popover.
   */
  public abstract val aiPopoverShadowOuter02: Color

  /**
   * Skeleton color for AI containers.
   */
  public abstract val aiSkeletonBackground: Color

  /**
   * Skeleton color for AI text and UI elements.
   */
  public abstract val aiSkeletonElement: Color

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
   * Chat avatar background color for agents.
   */
  public abstract val chatAvatarAgent: Color

  /**
   * Chat avatar background color for bots.
   */
  public abstract val chatAvatarBot: Color

  /**
   * Chat avatar background color for users.
   */
  public abstract val chatAvatarUser: Color

  /**
   * Chat bubble background color for agents.
   */
  public abstract val chatBubbleAgent: Color

  /**
   * Chat bubble border color for agents.
   */
  public abstract val chatBubbleBorder: Color

  /**
   * Chat bubble background color for users.
   */
  public abstract val chatBubbleUser: Color

  /**
   * Chat header background color.
   */
  public abstract val chatHeaderBackground: Color

  /**
   * Background color for chat prompt input.
   */
  public abstract val chatPromptBackground: Color

  /**
   * Linear gradient end value for chat prompts border.
   */
  public abstract val chatPromptBorderEnd: Color

  /**
   * Linear gradient start value for chat prompts border.
   */
  public abstract val chatPromptBorderStart: Color

  /**
   * Chat shell background color.
   */
  public abstract val chatShellBackground: Color

  /**
   * Chat quick action button color.
   */
  public abstract val chatButton: Color

  /**
   * Active color for [chatButton].
   */
  public abstract val chatButtonActive: Color

  /**
   * Hover color for [chatButton].
   */
  public abstract val chatButtonHover: Color

  /**
   * Selected color for [chatButton].
   */
  public abstract val chatButtonSelected: Color

  /**
   * Text color for hovered chat button.
   */
  public abstract val chatButtonTextHover: Color

  /**
   * Text color for selected chat-button.
   */
  public abstract val chatButtonTextSelected: Color

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
   * Hover for notification ghost button.
   */
  public abstract val notificationActionHover: Color

  /**
   * Tertiary button color for notification.
   */
  public abstract val notificationActionTertiaryInverse: Color

  /**
   * Active color for tertiary button in notification.
   */
  public abstract val notificationActionTertiaryInverseActive: Color

  /**
   * Hover color for tertiary button in notification.
   */
  public abstract val notificationActionTertiaryInverseHover: Color

  /**
   * Text color for tertiary button in notification.
   */
  public abstract val notificationActionTertiaryInverseText: Color

  /**
   * Disabled color for tertiary button in notification.
   */
  public abstract val notificationActionTertiaryInverseTextOnColorDisabled: Color

  /**
   * Error low contrast notification background.
   */
  public abstract val notificationBackgroundError: Color

  /**
   * Informational low contrast notification background.
   */
  public abstract val notificationBackgroundInfo: Color

  /**
   * Success low contrast notification background.
   */
  public abstract val notificationBackgroundSuccess: Color

  /**
   * Warning low contrast notification background.
   */
  public abstract val notificationBackgroundWarning: Color

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
   * Blue tag background.
   */
  public abstract val tagBackgroundBlue: Color

  /**
   * Cool gray tag background.
   */
  public abstract val tagBackgroundCoolGray: Color

  /**
   * Cyan tag background.
   */
  public abstract val tagBackgroundCyan: Color

  /**
   * Gray tag background.
   */
  public abstract val tagBackgroundGray: Color

  /**
   * Green tag background.
   */
  public abstract val tagBackgroundGreen: Color

  /**
   * Magenta tag background.
   */
  public abstract val tagBackgroundMagenta: Color

  /**
   * Purple tag background.
   */
  public abstract val tagBackgroundPurple: Color

  /**
   * Red tag background.
   */
  public abstract val tagBackgroundRed: Color

  /**
   * Teal tag background.
   */
  public abstract val tagBackgroundTeal: Color

  /**
   * Warm gray tag background.
   */
  public abstract val tagBackgroundWarmGray: Color

  /**
   * Blue tag border for operational tag.
   */
  public abstract val tagBorderBlue: Color

  /**
   * Cool gray tag border for operational tag.
   */
  public abstract val tagBorderCoolGray: Color

  /**
   * Cyan tag border for operational tag.
   */
  public abstract val tagBorderCyan: Color

  /**
   * Gray tag border for operational tag.
   */
  public abstract val tagBorderGray: Color

  /**
   * Green tag border for operational tag.
   */
  public abstract val tagBorderGreen: Color

  /**
   * Magenta tag border for operational tag.
   */
  public abstract val tagBorderMagenta: Color

  /**
   * Purple tag border for operational tag.
   */
  public abstract val tagBorderPurple: Color

  /**
   * Red tag border for operational tag.
   */
  public abstract val tagBorderRed: Color

  /**
   * Teal tag border for operational tag.
   */
  public abstract val tagBorderTeal: Color

  /**
   * Warm gray tag border for operational tag.
   */
  public abstract val tagBorderWarmGray: Color

  /**
   * Blue tag text.
   * Blue tag icon.
   */
  public abstract val tagColorBlue: Color

  /**
   * Cool gray tag text.
   * Cool gray tag icon.
   */
  public abstract val tagColorCoolGray: Color

  /**
   * Cyan tag text.
   * Cyan tag icon.
   */
  public abstract val tagColorCyan: Color

  /**
   * Gray tag text.
   * Gray tag icon.
   */
  public abstract val tagColorGray: Color

  /**
   * Green tag text.
   * Green tag icon.
   */
  public abstract val tagColorGreen: Color

  /**
   * Magenta tag text.
   * Magenta tag icon.
   */
  public abstract val tagColorMagenta: Color

  /**
   * Purple tag text.
   * Purple tag icon.
   */
  public abstract val tagColorPurple: Color

  /**
   * Red tag text.
   * Red tag icon.
   */
  public abstract val tagColorRed: Color

  /**
   * Teal tag text.
   * Teal tag icon.
   */
  public abstract val tagColorTeal: Color

  /**
   * Warm gray tag text.
   * Warm gray tag icon.
   */
  public abstract val tagColorWarmGray: Color

  /**
   * Blue tag hover for [tagBackgroundBlue].
   */
  public abstract val tagHoverBlue: Color

  /**
   * Cool gray tag hover for [tagBackgroundCoolGray].
   */
  public abstract val tagHoverCoolGray: Color

  /**
   * Cyan tag hover for [tagBackgroundCyan].
   */
  public abstract val tagHoverCyan: Color

  /**
   * Gray tag hover for [tagBackgroundGray].
   */
  public abstract val tagHoverGray: Color

  /**
   * Green tag hover for [tagBackgroundGreen].
   */
  public abstract val tagHoverGreen: Color

  /**
   * Magenta tag hover for [tagBackgroundMagenta].
   */
  public abstract val tagHoverMagenta: Color

  /**
   * Purple tag hover for [tagBackgroundPurple].
   */
  public abstract val tagHoverPurple: Color

  /**
   * Red tag hover for [tagBackgroundRed].
   */
  public abstract val tagHoverRed: Color

  /**
   * Teal tag hover for [tagBackgroundTeal].
   */
  public abstract val tagHoverTeal: Color

  /**
   * Warm gray tag hover for [tagBackgroundWarmGray].
   */
  public abstract val tagHoverWarmGray: Color

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
    aiAuraEnd: Color = this.aiAuraEnd,
    aiAuraHoverBackground: Color = this.aiAuraHoverBackground,
    aiAuraHoverEnd: Color = this.aiAuraHoverEnd,
    aiAuraHoverStart: Color = this.aiAuraHoverStart,
    aiAuraStart: Color = this.aiAuraStart,
    aiAuraStartSm: Color = this.aiAuraStartSm,
    aiBorderEnd: Color = this.aiBorderEnd,
    aiBorderStart: Color = this.aiBorderStart,
    aiBorderStrong: Color = this.aiBorderStrong,
    aiDropShadow: Color = this.aiDropShadow,
    aiInnerShadow: Color = this.aiInnerShadow,
    aiOverlay: Color = this.aiOverlay,
    aiPopoverBackground: Color = this.aiPopoverBackground,
    aiPopoverShadowOuter01: Color = this.aiPopoverShadowOuter01,
    aiPopoverShadowOuter02: Color = this.aiPopoverShadowOuter02,
    aiSkeletonBackground: Color = this.aiSkeletonBackground,
    aiSkeletonElement: Color = this.aiSkeletonElement,
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
    chatAvatarAgent: Color = this.chatAvatarAgent,
    chatAvatarBot: Color = this.chatAvatarBot,
    chatAvatarUser: Color = this.chatAvatarUser,
    chatBubbleAgent: Color = this.chatBubbleAgent,
    chatBubbleBorder: Color = this.chatBubbleBorder,
    chatBubbleUser: Color = this.chatBubbleUser,
    chatHeaderBackground: Color = this.chatHeaderBackground,
    chatPromptBackground: Color = this.chatPromptBackground,
    chatPromptBorderEnd: Color = this.chatPromptBorderEnd,
    chatPromptBorderStart: Color = this.chatPromptBorderStart,
    chatShellBackground: Color = this.chatShellBackground,
    chatButton: Color = this.chatButton,
    chatButtonActive: Color = this.chatButtonActive,
    chatButtonHover: Color = this.chatButtonHover,
    chatButtonSelected: Color = this.chatButtonSelected,
    chatButtonTextHover: Color = this.chatButtonTextHover,
    chatButtonTextSelected: Color = this.chatButtonTextSelected,
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
    notificationActionHover: Color = this.notificationActionHover,
    notificationActionTertiaryInverse: Color = this.notificationActionTertiaryInverse,
    notificationActionTertiaryInverseActive: Color = this.notificationActionTertiaryInverseActive,
    notificationActionTertiaryInverseHover: Color = this.notificationActionTertiaryInverseHover,
    notificationActionTertiaryInverseText: Color = this.notificationActionTertiaryInverseText,
    notificationActionTertiaryInverseTextOnColorDisabled: Color =
        this.notificationActionTertiaryInverseTextOnColorDisabled,
    notificationBackgroundError: Color = this.notificationBackgroundError,
    notificationBackgroundInfo: Color = this.notificationBackgroundInfo,
    notificationBackgroundSuccess: Color = this.notificationBackgroundSuccess,
    notificationBackgroundWarning: Color = this.notificationBackgroundWarning,
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
    tagBackgroundBlue: Color = this.tagBackgroundBlue,
    tagBackgroundCoolGray: Color = this.tagBackgroundCoolGray,
    tagBackgroundCyan: Color = this.tagBackgroundCyan,
    tagBackgroundGray: Color = this.tagBackgroundGray,
    tagBackgroundGreen: Color = this.tagBackgroundGreen,
    tagBackgroundMagenta: Color = this.tagBackgroundMagenta,
    tagBackgroundPurple: Color = this.tagBackgroundPurple,
    tagBackgroundRed: Color = this.tagBackgroundRed,
    tagBackgroundTeal: Color = this.tagBackgroundTeal,
    tagBackgroundWarmGray: Color = this.tagBackgroundWarmGray,
    tagBorderBlue: Color = this.tagBorderBlue,
    tagBorderCoolGray: Color = this.tagBorderCoolGray,
    tagBorderCyan: Color = this.tagBorderCyan,
    tagBorderGray: Color = this.tagBorderGray,
    tagBorderGreen: Color = this.tagBorderGreen,
    tagBorderMagenta: Color = this.tagBorderMagenta,
    tagBorderPurple: Color = this.tagBorderPurple,
    tagBorderRed: Color = this.tagBorderRed,
    tagBorderTeal: Color = this.tagBorderTeal,
    tagBorderWarmGray: Color = this.tagBorderWarmGray,
    tagColorBlue: Color = this.tagColorBlue,
    tagColorCoolGray: Color = this.tagColorCoolGray,
    tagColorCyan: Color = this.tagColorCyan,
    tagColorGray: Color = this.tagColorGray,
    tagColorGreen: Color = this.tagColorGreen,
    tagColorMagenta: Color = this.tagColorMagenta,
    tagColorPurple: Color = this.tagColorPurple,
    tagColorRed: Color = this.tagColorRed,
    tagColorTeal: Color = this.tagColorTeal,
    tagColorWarmGray: Color = this.tagColorWarmGray,
    tagHoverBlue: Color = this.tagHoverBlue,
    tagHoverCoolGray: Color = this.tagHoverCoolGray,
    tagHoverCyan: Color = this.tagHoverCyan,
    tagHoverGray: Color = this.tagHoverGray,
    tagHoverGreen: Color = this.tagHoverGreen,
    tagHoverMagenta: Color = this.tagHoverMagenta,
    tagHoverPurple: Color = this.tagHoverPurple,
    tagHoverRed: Color = this.tagHoverRed,
    tagHoverTeal: Color = this.tagHoverTeal,
    tagHoverWarmGray: Color = this.tagHoverWarmGray,
    textDisabled: Color = this.textDisabled,
    textError: Color = this.textError,
    textHelper: Color = this.textHelper,
    textInverse: Color = this.textInverse,
    textOnColor: Color = this.textOnColor,
    textOnColorDisabled: Color = this.textOnColorDisabled,
    textPlaceholder: Color = this.textPlaceholder,
    textPrimary: Color = this.textPrimary,
    textSecondary: Color = this.textSecondary,
  ): Theme = object : Theme() {
    override val aiAuraEnd: Color = aiAuraEnd
    override val aiAuraHoverBackground: Color = aiAuraHoverBackground
    override val aiAuraHoverEnd: Color = aiAuraHoverEnd
    override val aiAuraHoverStart: Color = aiAuraHoverStart
    override val aiAuraStart: Color = aiAuraStart
    override val aiAuraStartSm: Color = aiAuraStartSm
    override val aiBorderEnd: Color = aiBorderEnd
    override val aiBorderStart: Color = aiBorderStart
    override val aiBorderStrong: Color = aiBorderStrong
    override val aiDropShadow: Color = aiDropShadow
    override val aiInnerShadow: Color = aiInnerShadow
    override val aiOverlay: Color = aiOverlay
    override val aiPopoverBackground: Color = aiPopoverBackground
    override val aiPopoverShadowOuter01: Color = aiPopoverShadowOuter01
    override val aiPopoverShadowOuter02: Color = aiPopoverShadowOuter02
    override val aiSkeletonBackground: Color = aiSkeletonBackground
    override val aiSkeletonElement: Color = aiSkeletonElement
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
    override val chatAvatarAgent: Color = chatAvatarAgent
    override val chatAvatarBot: Color = chatAvatarBot
    override val chatAvatarUser: Color = chatAvatarUser
    override val chatBubbleAgent: Color = chatBubbleAgent
    override val chatBubbleBorder: Color = chatBubbleBorder
    override val chatBubbleUser: Color = chatBubbleUser
    override val chatHeaderBackground: Color = chatHeaderBackground
    override val chatPromptBackground: Color = chatPromptBackground
    override val chatPromptBorderEnd: Color = chatPromptBorderEnd
    override val chatPromptBorderStart: Color = chatPromptBorderStart
    override val chatShellBackground: Color = chatShellBackground
    override val chatButton: Color = chatButton
    override val chatButtonActive: Color = chatButtonActive
    override val chatButtonHover: Color = chatButtonHover
    override val chatButtonSelected: Color = chatButtonSelected
    override val chatButtonTextHover: Color = chatButtonTextHover
    override val chatButtonTextSelected: Color = chatButtonTextSelected
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
    override val notificationActionHover: Color = notificationActionHover
    override val notificationActionTertiaryInverse: Color = notificationActionTertiaryInverse
    override val notificationActionTertiaryInverseActive: Color =
      notificationActionTertiaryInverseActive
    override val notificationActionTertiaryInverseHover: Color =
      notificationActionTertiaryInverseHover
    override val notificationActionTertiaryInverseText: Color =
      notificationActionTertiaryInverseText
    override val notificationActionTertiaryInverseTextOnColorDisabled: Color =
      notificationActionTertiaryInverseTextOnColorDisabled
    override val notificationBackgroundError: Color = notificationBackgroundError
    override val notificationBackgroundInfo: Color = notificationBackgroundInfo
    override val notificationBackgroundSuccess: Color = notificationBackgroundSuccess
    override val notificationBackgroundWarning: Color = notificationBackgroundWarning
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
    override val tagBackgroundBlue: Color = tagBackgroundBlue
    override val tagBackgroundCoolGray: Color = tagBackgroundCoolGray
    override val tagBackgroundCyan: Color = tagBackgroundCyan
    override val tagBackgroundGray: Color = tagBackgroundGray
    override val tagBackgroundGreen: Color = tagBackgroundGreen
    override val tagBackgroundMagenta: Color = tagBackgroundMagenta
    override val tagBackgroundPurple: Color = tagBackgroundPurple
    override val tagBackgroundRed: Color = tagBackgroundRed
    override val tagBackgroundTeal: Color = tagBackgroundTeal
    override val tagBackgroundWarmGray: Color = tagBackgroundWarmGray
    override val tagBorderBlue: Color = tagBorderBlue
    override val tagBorderCoolGray: Color = tagBorderCoolGray
    override val tagBorderCyan: Color = tagBorderCyan
    override val tagBorderGray: Color = tagBorderGray
    override val tagBorderGreen: Color = tagBorderGreen
    override val tagBorderMagenta: Color = tagBorderMagenta
    override val tagBorderPurple: Color = tagBorderPurple
    override val tagBorderRed: Color = tagBorderRed
    override val tagBorderTeal: Color = tagBorderTeal
    override val tagBorderWarmGray: Color = tagBorderWarmGray
    override val tagColorBlue: Color = tagColorBlue
    override val tagColorCoolGray: Color = tagColorCoolGray
    override val tagColorCyan: Color = tagColorCyan
    override val tagColorGray: Color = tagColorGray
    override val tagColorGreen: Color = tagColorGreen
    override val tagColorMagenta: Color = tagColorMagenta
    override val tagColorPurple: Color = tagColorPurple
    override val tagColorRed: Color = tagColorRed
    override val tagColorTeal: Color = tagColorTeal
    override val tagColorWarmGray: Color = tagColorWarmGray
    override val tagHoverBlue: Color = tagHoverBlue
    override val tagHoverCoolGray: Color = tagHoverCoolGray
    override val tagHoverCyan: Color = tagHoverCyan
    override val tagHoverGray: Color = tagHoverGray
    override val tagHoverGreen: Color = tagHoverGreen
    override val tagHoverMagenta: Color = tagHoverMagenta
    override val tagHoverPurple: Color = tagHoverPurple
    override val tagHoverRed: Color = tagHoverRed
    override val tagHoverTeal: Color = tagHoverTeal
    override val tagHoverWarmGray: Color = tagHoverWarmGray
    override val textDisabled: Color = textDisabled
    override val textError: Color = textError
    override val textHelper: Color = textHelper
    override val textInverse: Color = textInverse
    override val textOnColor: Color = textOnColor
    override val textOnColorDisabled: Color = textOnColorDisabled
    override val textPlaceholder: Color = textPlaceholder
    override val textPrimary: Color = textPrimary
    override val textSecondary: Color = textSecondary
  }
}
