package dev.gabrieldrn.carbon.color

import androidx.compose.ui.graphics.Color

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
public interface Theme {

    // region Background

    /**
     * Default page background;
     * UI shell base color.
     */
    public val background: Color

    /**
     * Hover color for [background];
     * Hover color for transparent backgrounds.
     */
    public val backgroundHover: Color

    /**
     * Active color for [background].
     */
    public val backgroundActive: Color

    /**
     * Selected color for [background].
     */
    public val backgroundSelected: Color

    /**
     * Hover color for [backgroundSelected].
     */
    public val backgroundSelectedHover: Color

    /**
     * High contrast backgrounds;
     * High contrast elements.
     */
    public val backgroundInverse: Color

    /**
     * Hover color for [backgroundInverse].
     */
    public val backgroundInverseHover: Color

    /**
     * Feature background color.
     */
    public val backgroundBrand: Color

    // endregion

    // region Layer

    /**
     * Container color on [background];
     * Secondary page background.
     */
    public val layer01: Color

    /**
     * Container color on [layer01].
     */
    public val layer02: Color

    /**
     * Container color on [layer02].
     */
    public val layer03: Color

    /**
     * Hover color for [layer01].
     */
    public val layerHover01: Color

    /**
     * Hover color for [layer02].
     */
    public val layerHover02: Color

    /**
     * Hover color for [layer03].
     */
    public val layerHover03: Color

    /**
     * Active color for [layer01].
     */
    public val layerActive01: Color

    /**
     * Active color for [layer02].
     */
    public val layerActive02: Color

    /**
     * Active color for [layer03].
     */
    public val layerActive03: Color

    /**
     * Selected color for [layer01].
     */
    public val layerSelected01: Color

    /**
     * Selected color for [layer02].
     */
    public val layerSelected02: Color

    /**
     * Selected color for [layer03].
     */
    public val layerSelected03: Color

    /**
     * Hover color for [layerSelected01].
     */
    public val layerSelectedHover01: Color

    /**
     * Hover color for [layerSelected02].
     */
    public val layerSelectedHover02: Color

    /**
     * Hover color for [layerSelected03].
     */
    public val layerSelectedHover03: Color

    /**
     * High contrast elements;
     * 4.5:1 AA element contrast.
     */
    public val layerSelectedInverse: Color

    /**
     * Disabled color for selected layers.
     */
    public val layerSelectedDisabled: Color

    // endregion

    // region Layer accent

    /**
     * Tertiary background paired with [layer01].
     */
    public val layerAccent01: Color

    /**
     * Tertiary background paired with [layer02].
     */
    public val layerAccent02: Color

    /**
     * Tertiary background paired with [layer03].
     */
    public val layerAccent03: Color

    /**
     * Hover color for [layerAccent01].
     */
    public val layerAccentHover01: Color

    /**
     * Hover color for [layerAccent02].
     */
    public val layerAccentHover02: Color

    /**
     * Hover color for [layerAccent03].
     */
    public val layerAccentHover03: Color

    /**
     * Active color for [layerAccent01].
     */
    public val layerAccentActive01: Color

    /**
     * Active color for [layerAccent02].
     */
    public val layerAccentActive02: Color

    /**
     * Active color for [layerAccent03].
     */
    public val layerAccentActive03: Color

    // endregion

    // region Field

    /**
     * Default input fields;
     * Fields on [background].
     */
    public val field01: Color

    /**
     * Secondary input fields;
     * Fields on [layer01].
     */
    public val field02: Color

    /**
     * Tertiary input fields;
     * Fields on [layer02].
     */
    public val field03: Color

    /**
     * Hover color for [field01].
     */
    public val fieldHover01: Color

    /**
     * Hover color for [field02].
     */
    public val fieldHover02: Color

    /**
     * Hover color for [field03].
     */
    public val fieldHover03: Color

    // endregion

    // region Border

    /**
     * 3:1 AA contrast;
     * Selected borders;
     * Active borders.
     */
    public val borderInteractive: Color

    /**
     * Subtle borders paired with $background.
     */
    public val borderSubtle00: Color

    /**
     * Subtle borders paired with [layer01].
     */
    public val borderSubtle01: Color

    /**
     * Subtle borders paired with [layer02].
     */
    public val borderSubtle02: Color

    /**
     * Subtle borders paired with [layer03].
     */
    public val borderSubtle03: Color

    /**
     * Selected color for [borderSubtle01].
     */
    public val borderSubtleSelected01: Color

    /**
     * Selected color for [borderSubtle02].
     */
    public val borderSubtleSelected02: Color

    /**
     * Selected color for [borderSubtle03].
     */
    public val borderSubtleSelected03: Color

    /**
     * Medium contrast border;
     * Border-bottom paired with [field01];
     * 3:1 AA non-text contrast.
     */
    public val borderStrong01: Color

    /**
     * Medium contrast border;
     * Border-bottom paired with [field02];
     * 3:1 AA non-text contrast.
     */
    public val borderStrong02: Color

    /**
     * Medium contrast border;
     * Border-bottom paired with [field03];
     * 3:1 AA non-text contrast.
     */
    public val borderStrong03: Color

    /**
     * Operable tile indicator paired with [layer01].
     */
    public val borderTile01: Color

    /**
     * Operable tile indicator paired with [layer02].
     */
    public val borderTile02: Color

    /**
     * Operable tile indicator paired with [layer03].
     */
    public val borderTile03: Color

    /**
     * High contrast border;
     * 4.5:1 AA non-text contrast.
     */
    public val borderInverse: Color

    /**
     * Disabled border color (excluding border-subtles).
     */
    public val borderDisabled: Color

    // endregion

    // region Text

    /**
     * Primary text;
     * Body copy;
     * Headers;
     * Hover text color for [textSecondary].
     */
    public val textPrimary: Color

    /**
     * Secondary text;
     * Input labels.
     */
    public val textSecondary: Color

    /**
     * Placeholder text.
     */
    public val textPlaceholder: Color

    /**
     * Text on interactive colors;
     * Text on button colors.
     */
    public val textOnColor: Color

    /**
     * Disabled color for [textOnColor].
     */
    public val textOnColorDisabled: Color

    /**
     * Tertiary text;
     * Help text.
     */
    public val textHelper: Color

    /**
     * Error message text.
     */
    public val textError: Color

    /**
     * Inverse text color.
     */
    public val textInverse: Color

    /**
     * Disabled text color.
     */
    public val textDisabled: Color

    // endregion

    // region Link

    /**
     * Primary links.
     */
    public val linkPrimary: Color

    /**
     * Hover color for [linkPrimary].
     */
    public val linkPrimaryHover: Color

    /**
     * Secondary link color for lower contrast backgrounds.
     */
    public val linkSecondary: Color

    /**
     * Links on [backgroundInverse] backgrounds.
     */
    public val linkInverse: Color

    /**
     * Color for visited links.
     */
    public val linkVisited: Color

    // endregion

    // region Icon

    /**
     * Primary icons.
     */
    public val iconPrimary: Color

    /**
     * Secondary icons.
     */
    public val iconSecondary: Color

    /**
     *
     * Icons on interactive colors;
     * Icons on non-layer colors.
     */
    public val iconOnColor: Color

    /**
     * Disabled color for [iconOnColor].
     */
    public val iconOnColorDisabled: Color

    /**
     * Icons that indicate operability.
     */
    public val iconInteractive: Color

    /**
     * Inverse icon color.
     */
    public val iconInverse: Color

    /**
     * Disabled icon color.
     */
    public val iconDisabled: Color

    // endregion

    // region Button

    /**
     * Primary button color.
     */
    public val buttonPrimary: Color

    /**
     * Hover color for [buttonPrimary].
     */
    public val buttonPrimaryHover: Color

    /**
     * Active color for [buttonPrimary].
     */
    public val buttonPrimaryActive: Color

    /**
     * Secondary button color.
     */
    public val buttonSecondary: Color

    /**
     * Hover color for [buttonSecondary].
     */
    public val buttonSecondaryHover: Color

    /**
     * Active color for [buttonSecondary].
     */
    public val buttonSecondaryActive: Color

    /**
     * Tertiary button color;
     * 4.5:1 AA text contrast.
     */
    public val buttonTertiary: Color

    /**
     * Hover color for [buttonTertiary].
     */
    public val buttonTertiaryHover: Color

    /**
     * Active color for [buttonTertiary].
     */
    public val buttonTertiaryActive: Color

    /**
     * Primary danger button color;
     * 3:1 AA non-text contrast.
     */
    public val buttonDangerPrimary: Color

    /**
     * Tertiary danger button color;
     * Ghost danger button color;
     * 4.5:1 AA text contrast.
     */
    public val buttonDangerSecondary: Color

    /**
     * Hover color for [buttonDangerPrimary];
     * Hover color for [buttonDangerSecondary].
     */
    public val buttonDangerHover: Color

    /**
     * Active color for [buttonDangerPrimary];
     * Active color for [buttonDangerSecondary].
     */
    public val buttonDangerActive: Color

    /**
     * Fluid button separator;
     * 3:1 AA non-text contrast.
     */
    public val buttonSeparator: Color

    /**
     * Disabled color for button elements.
     */
    public val buttonDisabled: Color

    // endregion

    // region Support

    /**
     * Error;
     * Invalid state.
     */
    public val supportError: Color

    /**
     * Success;
     * On.
     */
    public val supportSuccess: Color

    /**
     * Warning;
     * Caution.
     */
    public val supportWarning: Color

    /**
     * Information.
     */
    public val supportInfo: Color

    /**
     * Error in high contrast moments.
     */
    public val supportErrorInverse: Color

    /**
     * Success in high contrast moments.
     */
    public val supportSuccessInverse: Color

    /**
     * Warning in high contrast moments.
     */
    public val supportWarningInverse: Color

    /**
     * Information in high contrast moments.
     */
    public val supportInfoInverse: Color

    // endregion

    // region Focus

    /**
     * Focus border;
     * Focus underline.
     */
    public val focus: Color

    /**
     * Contrast border paired with [focus].
     */
    public val focusInset: Color

    /**
     * Focus on high contrast moments.
     */
    public val focusInverse: Color

    // endregion

    // region Miscellaneous

    /**
     * 3:1 AA contrast;
     * Selected elements;
     * Active elements;
     * Accent icons.
     */
    public val interactive: Color

    /**
     * Highlight color.
     */
    public val highlight: Color

    /**
     * Off background;
     * 3:1 AA contrast.
     */
    public val toggleOff: Color

    /**
     * Background overlay.
     */
    public val overlay: Color

    /**
     * Skeleton color for text and UI elements.
     */
    public val skeletonElement: Color

    /**
     * Skeleton color for containers.
     */
    public val skeletonBackground: Color

    // endregion

}
