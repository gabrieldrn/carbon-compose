/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.carbon.foundation.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.tag.colors.TagColors

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

    // ---
    // Core tokens
    // ---

    // region Background

    /**
     * Default page background;
     * UI shell base color.
     */
    public abstract val background: Color

    /**
     * Hover color for [background];
     * Hover color for transparent backgrounds.
     */
    public abstract val backgroundHover: Color

    /**
     * Active color for [background].
     */
    public abstract val backgroundActive: Color

    /**
     * Selected color for [background].
     */
    public abstract val backgroundSelected: Color

    /**
     * Hover color for [backgroundSelected].
     */
    public abstract val backgroundSelectedHover: Color

    /**
     * High contrast backgrounds;
     * High contrast elements.
     */
    public abstract val backgroundInverse: Color

    /**
     * Hover color for [backgroundInverse].
     */
    public abstract val backgroundInverseHover: Color

    /**
     * Feature background color.
     */
    public abstract val backgroundBrand: Color

    // endregion

    // region Layer

    /**
     * Container color on [background];
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
     * High contrast elements;
     * 4.5:1 AA element contrast.
     */
    public abstract val layerSelectedInverse: Color

    /**
     * Disabled color for selected layers.
     */
    public abstract val layerSelectedDisabled: Color

    // endregion

    // region Layer accent

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

    // endregion

    // region Field

    /**
     * Default input fields;
     * Fields on [background].
     */
    public abstract val field01: Color

    /**
     * Secondary input fields;
     * Fields on [layer01].
     */
    public abstract val field02: Color

    /**
     * Tertiary input fields;
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

    // endregion

    // region Border

    /**
     * 3:1 AA contrast;
     * Selected borders;
     * Active borders.
     */
    public abstract val borderInteractive: Color

    /**
     * Subtle borders paired with $background.
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
     * Medium contrast border;
     * Border-bottom paired with [field01];
     * 3:1 AA non-text contrast.
     */
    public abstract val borderStrong01: Color

    /**
     * Medium contrast border;
     * Border-bottom paired with [field02];
     * 3:1 AA non-text contrast.
     */
    public abstract val borderStrong02: Color

    /**
     * Medium contrast border;
     * Border-bottom paired with [field03];
     * 3:1 AA non-text contrast.
     */
    public abstract val borderStrong03: Color

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
     * High contrast border;
     * 4.5:1 AA non-text contrast.
     */
    public abstract val borderInverse: Color

    /**
     * Disabled border color (excluding border-subtles).
     */
    public abstract val borderDisabled: Color

    // endregion

    // region Text

    /**
     * Primary text;
     * Body copy;
     * Headers;
     * Hover text color for [textSecondary].
     */
    public abstract val textPrimary: Color

    /**
     * Secondary text;
     * Input labels.
     */
    public abstract val textSecondary: Color

    /**
     * Placeholder text.
     */
    public abstract val textPlaceholder: Color

    /**
     * Text on interactive colors;
     * Text on button colors.
     */
    public abstract val textOnColor: Color

    /**
     * Disabled color for [textOnColor].
     */
    public abstract val textOnColorDisabled: Color

    /**
     * Tertiary text;
     * Help text.
     */
    public abstract val textHelper: Color

    /**
     * Error message text.
     */
    public abstract val textError: Color

    /**
     * Inverse text color.
     */
    public abstract val textInverse: Color

    /**
     * Disabled text color.
     */
    public abstract val textDisabled: Color

    // endregion

    // region Link

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
     * Links on [backgroundInverse] backgrounds.
     */
    public abstract val linkInverse: Color

    /**
     * Color for visited links.
     */
    public abstract val linkVisited: Color

    // endregion

    // region Icon

    /**
     * Primary icons.
     */
    public abstract val iconPrimary: Color

    /**
     * Secondary icons.
     */
    public abstract val iconSecondary: Color

    /**
     *
     * Icons on interactive colors;
     * Icons on non-layer colors.
     */
    public abstract val iconOnColor: Color

    /**
     * Disabled color for [iconOnColor].
     */
    public abstract val iconOnColorDisabled: Color

    /**
     * Icons that indicate operability.
     */
    public abstract val iconInteractive: Color

    /**
     * Inverse icon color.
     */
    public abstract val iconInverse: Color

    /**
     * Disabled icon color.
     */
    public abstract val iconDisabled: Color

    // endregion

    // region Button

    /**
     * Primary button color.
     */
    public abstract val buttonPrimary: Color

    /**
     * Hover color for [buttonPrimary].
     */
    public abstract val buttonPrimaryHover: Color

    /**
     * Active color for [buttonPrimary].
     */
    public abstract val buttonPrimaryActive: Color

    /**
     * Secondary button color.
     */
    public abstract val buttonSecondary: Color

    /**
     * Hover color for [buttonSecondary].
     */
    public abstract val buttonSecondaryHover: Color

    /**
     * Active color for [buttonSecondary].
     */
    public abstract val buttonSecondaryActive: Color

    /**
     * Tertiary button color;
     * 4.5:1 AA text contrast.
     */
    public abstract val buttonTertiary: Color

    /**
     * Hover color for [buttonTertiary].
     */
    public abstract val buttonTertiaryHover: Color

    /**
     * Active color for [buttonTertiary].
     */
    public abstract val buttonTertiaryActive: Color

    /**
     * Primary danger button color;
     * 3:1 AA non-text contrast.
     */
    public abstract val buttonDangerPrimary: Color

    /**
     * Tertiary danger button color;
     * Ghost danger button color;
     * 4.5:1 AA text contrast.
     */
    public abstract val buttonDangerSecondary: Color

    /**
     * Hover color for [buttonDangerPrimary];
     * Hover color for [buttonDangerSecondary].
     */
    public abstract val buttonDangerHover: Color

    /**
     * Active color for [buttonDangerPrimary];
     * Active color for [buttonDangerSecondary].
     */
    public abstract val buttonDangerActive: Color

    /**
     * Fluid button separator;
     * 3:1 AA non-text contrast.
     */
    public abstract val buttonSeparator: Color

    /**
     * Disabled color for button elements.
     */
    public abstract val buttonDisabled: Color

    // endregion

    // region Support

    /**
     * Error;
     * Invalid state.
     */
    public abstract val supportError: Color

    /**
     * Success;
     * On.
     */
    public abstract val supportSuccess: Color

    /**
     * Warning;
     * Caution.
     */
    public abstract val supportWarning: Color

    /**
     * Information.
     */
    public abstract val supportInfo: Color

    /**
     * Error in high contrast moments.
     */
    public abstract val supportErrorInverse: Color

    /**
     * Success in high contrast moments.
     */
    public abstract val supportSuccessInverse: Color

    /**
     * Warning in high contrast moments.
     */
    public abstract val supportWarningInverse: Color

    /**
     * Information in high contrast moments.
     */
    public abstract val supportInfoInverse: Color

    // endregion

    // region Focus

    /**
     * Focus border;
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

    // endregion

    // region Miscellaneous

    /**
     * 3:1 AA contrast;
     * Selected elements;
     * Active elements;
     * Accent icons.
     */
    public abstract val interactive: Color

    /**
     * Highlight color.
     */
    public abstract val highlight: Color

    /**
     * Off background;
     * 3:1 AA contrast.
     */
    public abstract val toggleOff: Color

    /**
     * Background overlay.
     */
    public abstract val overlay: Color

    /**
     * Skeleton color for text and UI elements.
     */
    public abstract val skeletonElement: Color

    /**
     * Skeleton color for containers.
     */
    public abstract val skeletonBackground: Color

    // endregion

    // ---
    // Component tokens
    // ---

    /**
     * Tag component colors.
     */
    public abstract val tagColors: TagColors

    // ---
    // Utility functions
    // ---

    /**
     * Returns the container color based on a provided [layer].
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
        backgroundHover: Color = this.backgroundHover,
        backgroundActive: Color = this.backgroundActive,
        backgroundSelected: Color = this.backgroundSelected,
        backgroundSelectedHover: Color = this.backgroundSelectedHover,
        backgroundInverse: Color = this.backgroundInverse,
        backgroundInverseHover: Color = this.backgroundInverseHover,
        backgroundBrand: Color = this.backgroundBrand,
        layer01: Color = this.layer01,
        layer02: Color = this.layer02,
        layer03: Color = this.layer03,
        layerHover01: Color = this.layerHover01,
        layerHover02: Color = this.layerHover02,
        layerHover03: Color = this.layerHover03,
        layerActive01: Color = this.layerActive01,
        layerActive02: Color = this.layerActive02,
        layerActive03: Color = this.layerActive03,
        layerSelected01: Color = this.layerSelected01,
        layerSelected02: Color = this.layerSelected02,
        layerSelected03: Color = this.layerSelected03,
        layerSelectedHover01: Color = this.layerSelectedHover01,
        layerSelectedHover02: Color = this.layerSelectedHover02,
        layerSelectedHover03: Color = this.layerSelectedHover03,
        layerSelectedInverse: Color = this.layerSelectedInverse,
        layerSelectedDisabled: Color = this.layerSelectedDisabled,
        layerAccent01: Color = this.layerAccent01,
        layerAccent02: Color = this.layerAccent02,
        layerAccent03: Color = this.layerAccent03,
        layerAccentHover01: Color = this.layerAccentHover01,
        layerAccentHover02: Color = this.layerAccentHover02,
        layerAccentHover03: Color = this.layerAccentHover03,
        layerAccentActive01: Color = this.layerAccentActive01,
        layerAccentActive02: Color = this.layerAccentActive02,
        layerAccentActive03: Color = this.layerAccentActive03,
        field01: Color = this.field01,
        field02: Color = this.field02,
        field03: Color = this.field03,
        fieldHover01: Color = this.fieldHover01,
        fieldHover02: Color = this.fieldHover02,
        fieldHover03: Color = this.fieldHover03,
        borderInteractive: Color = this.borderInteractive,
        borderSubtle00: Color = this.borderSubtle00,
        borderSubtle01: Color = this.borderSubtle01,
        borderSubtle02: Color = this.borderSubtle02,
        borderSubtle03: Color = this.borderSubtle03,
        borderSubtleSelected01: Color = this.borderSubtleSelected01,
        borderSubtleSelected02: Color = this.borderSubtleSelected02,
        borderSubtleSelected03: Color = this.borderSubtleSelected03,
        borderStrong01: Color = this.borderStrong01,
        borderStrong02: Color = this.borderStrong02,
        borderStrong03: Color = this.borderStrong03,
        borderTile01: Color = this.borderTile01,
        borderTile02: Color = this.borderTile02,
        borderTile03: Color = this.borderTile03,
        borderInverse: Color = this.borderInverse,
        borderDisabled: Color = this.borderDisabled,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        textPlaceholder: Color = this.textPlaceholder,
        textOnColor: Color = this.textOnColor,
        textOnColorDisabled: Color = this.textOnColorDisabled,
        textHelper: Color = this.textHelper,
        textError: Color = this.textError,
        textInverse: Color = this.textInverse,
        textDisabled: Color = this.textDisabled,
        linkPrimary: Color = this.linkPrimary,
        linkPrimaryHover: Color = this.linkPrimaryHover,
        linkSecondary: Color = this.linkSecondary,
        linkInverse: Color = this.linkInverse,
        linkVisited: Color = this.linkVisited,
        iconPrimary: Color = this.iconPrimary,
        iconSecondary: Color = this.iconSecondary,
        iconOnColor: Color = this.iconOnColor,
        iconOnColorDisabled: Color = this.iconOnColorDisabled,
        iconInteractive: Color = this.iconInteractive,
        iconInverse: Color = this.iconInverse,
        iconDisabled: Color = this.iconDisabled,
        buttonPrimary: Color = this.buttonPrimary,
        buttonPrimaryHover: Color = this.buttonPrimaryHover,
        buttonPrimaryActive: Color = this.buttonPrimaryActive,
        buttonSecondary: Color = this.buttonSecondary,
        buttonSecondaryHover: Color = this.buttonSecondaryHover,
        buttonSecondaryActive: Color = this.buttonSecondaryActive,
        buttonTertiary: Color = this.buttonTertiary,
        buttonTertiaryHover: Color = this.buttonTertiaryHover,
        buttonTertiaryActive: Color = this.buttonTertiaryActive,
        buttonDangerPrimary: Color = this.buttonDangerPrimary,
        buttonDangerSecondary: Color = this.buttonDangerSecondary,
        buttonDangerHover: Color = this.buttonDangerHover,
        buttonDangerActive: Color = this.buttonDangerActive,
        buttonSeparator: Color = this.buttonSeparator,
        buttonDisabled: Color = this.buttonDisabled,
        supportError: Color = this.supportError,
        supportSuccess: Color = this.supportSuccess,
        supportWarning: Color = this.supportWarning,
        supportInfo: Color = this.supportInfo,
        supportErrorInverse: Color = this.supportErrorInverse,
        supportSuccessInverse: Color = this.supportSuccessInverse,
        supportWarningInverse: Color = this.supportWarningInverse,
        supportInfoInverse: Color = this.supportInfoInverse,
        focus: Color = this.focus,
        focusInset: Color = this.focusInset,
        focusInverse: Color = this.focusInverse,
        interactive: Color = this.interactive,
        highlight: Color = this.highlight,
        toggleOff: Color = this.toggleOff,
        overlay: Color = this.overlay,
        skeletonElement: Color = this.skeletonElement,
        skeletonBackground: Color = this.skeletonBackground,
        tagColors: TagColors = this.tagColors,
    ): Theme {
        return object : Theme() {
            override val background: Color = background
            override val backgroundHover: Color = backgroundHover
            override val backgroundActive: Color = backgroundActive
            override val backgroundSelected: Color = backgroundSelected
            override val backgroundSelectedHover: Color = backgroundSelectedHover
            override val backgroundInverse: Color = backgroundInverse
            override val backgroundInverseHover: Color = backgroundInverseHover
            override val backgroundBrand: Color = backgroundBrand
            override val layer01: Color = layer01
            override val layer02: Color = layer02
            override val layer03: Color = layer03
            override val layerHover01: Color = layerHover01
            override val layerHover02: Color = layerHover02
            override val layerHover03: Color = layerHover03
            override val layerActive01: Color = layerActive01
            override val layerActive02: Color = layerActive02
            override val layerActive03: Color = layerActive03
            override val layerSelected01: Color = layerSelected01
            override val layerSelected02: Color = layerSelected02
            override val layerSelected03: Color = layerSelected03
            override val layerSelectedHover01: Color = layerSelectedHover01
            override val layerSelectedHover02: Color = layerSelectedHover02
            override val layerSelectedHover03: Color = layerSelectedHover03
            override val layerSelectedInverse: Color = layerSelectedInverse
            override val layerSelectedDisabled: Color = layerSelectedDisabled
            override val layerAccent01: Color = layerAccent01
            override val layerAccent02: Color = layerAccent02
            override val layerAccent03: Color = layerAccent03
            override val layerAccentHover01: Color = layerAccentHover01
            override val layerAccentHover02: Color = layerAccentHover02
            override val layerAccentHover03: Color = layerAccentHover03
            override val layerAccentActive01: Color = layerAccentActive01
            override val layerAccentActive02: Color = layerAccentActive02
            override val layerAccentActive03: Color = layerAccentActive03
            override val field01: Color = field01
            override val field02: Color = field02
            override val field03: Color = field03
            override val fieldHover01: Color = fieldHover01
            override val fieldHover02: Color = fieldHover02
            override val fieldHover03: Color = fieldHover03
            override val borderInteractive: Color = borderInteractive
            override val borderSubtle00: Color = borderSubtle00
            override val borderSubtle01: Color = borderSubtle01
            override val borderSubtle02: Color = borderSubtle02
            override val borderSubtle03: Color = borderSubtle03
            override val borderSubtleSelected01: Color = borderSubtleSelected01
            override val borderSubtleSelected02: Color = borderSubtleSelected02
            override val borderSubtleSelected03: Color = borderSubtleSelected03
            override val borderStrong01: Color = borderStrong01
            override val borderStrong02: Color = borderStrong02
            override val borderStrong03: Color = borderStrong03
            override val borderTile01: Color = borderTile01
            override val borderTile02: Color = borderTile02
            override val borderTile03: Color = borderTile03
            override val borderInverse: Color = borderInverse
            override val borderDisabled: Color = borderDisabled
            override val textPrimary: Color = textPrimary
            override val textSecondary: Color = textSecondary
            override val textPlaceholder: Color = textPlaceholder
            override val textOnColor: Color = textOnColor
            override val textOnColorDisabled: Color = textOnColorDisabled
            override val textHelper: Color = textHelper
            override val textError: Color = textError
            override val textInverse: Color = textInverse
            override val textDisabled: Color = textDisabled
            override val linkPrimary: Color = linkPrimary
            override val linkPrimaryHover: Color = linkPrimaryHover
            override val linkSecondary: Color = linkSecondary
            override val linkInverse: Color = linkInverse
            override val linkVisited: Color = linkVisited
            override val iconPrimary: Color = iconPrimary
            override val iconSecondary: Color = iconSecondary
            override val iconOnColor: Color = iconOnColor
            override val iconOnColorDisabled: Color = iconOnColorDisabled
            override val iconInteractive: Color = iconInteractive
            override val iconInverse: Color = iconInverse
            override val iconDisabled: Color = iconDisabled
            override val buttonPrimary: Color = buttonPrimary
            override val buttonPrimaryHover: Color = buttonPrimaryHover
            override val buttonPrimaryActive: Color = buttonPrimaryActive
            override val buttonSecondary: Color = buttonSecondary
            override val buttonSecondaryHover: Color = buttonSecondaryHover
            override val buttonSecondaryActive: Color = buttonSecondaryActive
            override val buttonTertiary: Color = buttonTertiary
            override val buttonTertiaryHover: Color = buttonTertiaryHover
            override val buttonTertiaryActive: Color = buttonTertiaryActive
            override val buttonDangerPrimary: Color = buttonDangerPrimary
            override val buttonDangerSecondary: Color = buttonDangerSecondary
            override val buttonDangerHover: Color = buttonDangerHover
            override val buttonDangerActive: Color = buttonDangerActive
            override val buttonSeparator: Color = buttonSeparator
            override val buttonDisabled: Color = buttonDisabled
            override val supportError: Color = supportError
            override val supportSuccess: Color = supportSuccess
            override val supportWarning: Color = supportWarning
            override val supportInfo: Color = supportInfo
            override val supportErrorInverse: Color = supportErrorInverse
            override val supportSuccessInverse: Color = supportSuccessInverse
            override val supportWarningInverse: Color = supportWarningInverse
            override val supportInfoInverse: Color = supportInfoInverse
            override val focus: Color = focus
            override val focusInset: Color = focusInset
            override val focusInverse: Color = focusInverse
            override val interactive: Color = interactive
            override val highlight: Color = highlight
            override val toggleOff: Color = toggleOff
            override val overlay: Color = overlay
            override val skeletonElement: Color = skeletonElement
            override val skeletonBackground: Color = skeletonBackground
            override val tagColors: TagColors = tagColors
        }
    }

    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod", "LongMethod")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Theme) return false

        if (background != other.background) return false
        if (backgroundHover != other.backgroundHover) return false
        if (backgroundActive != other.backgroundActive) return false
        if (backgroundSelected != other.backgroundSelected) return false
        if (backgroundSelectedHover != other.backgroundSelectedHover) return false
        if (backgroundInverse != other.backgroundInverse) return false
        if (backgroundInverseHover != other.backgroundInverseHover) return false
        if (backgroundBrand != other.backgroundBrand) return false
        if (layer01 != other.layer01) return false
        if (layer02 != other.layer02) return false
        if (layer03 != other.layer03) return false
        if (layerHover01 != other.layerHover01) return false
        if (layerHover02 != other.layerHover02) return false
        if (layerHover03 != other.layerHover03) return false
        if (layerActive01 != other.layerActive01) return false
        if (layerActive02 != other.layerActive02) return false
        if (layerActive03 != other.layerActive03) return false
        if (layerSelected01 != other.layerSelected01) return false
        if (layerSelected02 != other.layerSelected02) return false
        if (layerSelected03 != other.layerSelected03) return false
        if (layerSelectedHover01 != other.layerSelectedHover01) return false
        if (layerSelectedHover02 != other.layerSelectedHover02) return false
        if (layerSelectedHover03 != other.layerSelectedHover03) return false
        if (layerSelectedInverse != other.layerSelectedInverse) return false
        if (layerSelectedDisabled != other.layerSelectedDisabled) return false
        if (layerAccent01 != other.layerAccent01) return false
        if (layerAccent02 != other.layerAccent02) return false
        if (layerAccent03 != other.layerAccent03) return false
        if (layerAccentHover01 != other.layerAccentHover01) return false
        if (layerAccentHover02 != other.layerAccentHover02) return false
        if (layerAccentHover03 != other.layerAccentHover03) return false
        if (layerAccentActive01 != other.layerAccentActive01) return false
        if (layerAccentActive02 != other.layerAccentActive02) return false
        if (layerAccentActive03 != other.layerAccentActive03) return false
        if (field01 != other.field01) return false
        if (field02 != other.field02) return false
        if (field03 != other.field03) return false
        if (fieldHover01 != other.fieldHover01) return false
        if (fieldHover02 != other.fieldHover02) return false
        if (fieldHover03 != other.fieldHover03) return false
        if (borderInteractive != other.borderInteractive) return false
        if (borderSubtle00 != other.borderSubtle00) return false
        if (borderSubtle01 != other.borderSubtle01) return false
        if (borderSubtle02 != other.borderSubtle02) return false
        if (borderSubtle03 != other.borderSubtle03) return false
        if (borderSubtleSelected01 != other.borderSubtleSelected01) return false
        if (borderSubtleSelected02 != other.borderSubtleSelected02) return false
        if (borderSubtleSelected03 != other.borderSubtleSelected03) return false
        if (borderStrong01 != other.borderStrong01) return false
        if (borderStrong02 != other.borderStrong02) return false
        if (borderStrong03 != other.borderStrong03) return false
        if (borderTile01 != other.borderTile01) return false
        if (borderTile02 != other.borderTile02) return false
        if (borderTile03 != other.borderTile03) return false
        if (borderInverse != other.borderInverse) return false
        if (borderDisabled != other.borderDisabled) return false
        if (textPrimary != other.textPrimary) return false
        if (textSecondary != other.textSecondary) return false
        if (textPlaceholder != other.textPlaceholder) return false
        if (textOnColor != other.textOnColor) return false
        if (textOnColorDisabled != other.textOnColorDisabled) return false
        if (textHelper != other.textHelper) return false
        if (textError != other.textError) return false
        if (textInverse != other.textInverse) return false
        if (textDisabled != other.textDisabled) return false
        if (linkPrimary != other.linkPrimary) return false
        if (linkPrimaryHover != other.linkPrimaryHover) return false
        if (linkSecondary != other.linkSecondary) return false
        if (linkInverse != other.linkInverse) return false
        if (linkVisited != other.linkVisited) return false
        if (iconPrimary != other.iconPrimary) return false
        if (iconSecondary != other.iconSecondary) return false
        if (iconOnColor != other.iconOnColor) return false
        if (iconOnColorDisabled != other.iconOnColorDisabled) return false
        if (iconInteractive != other.iconInteractive) return false
        if (iconInverse != other.iconInverse) return false
        if (iconDisabled != other.iconDisabled) return false
        if (buttonPrimary != other.buttonPrimary) return false
        if (buttonPrimaryHover != other.buttonPrimaryHover) return false
        if (buttonPrimaryActive != other.buttonPrimaryActive) return false
        if (buttonSecondary != other.buttonSecondary) return false
        if (buttonSecondaryHover != other.buttonSecondaryHover) return false
        if (buttonSecondaryActive != other.buttonSecondaryActive) return false
        if (buttonTertiary != other.buttonTertiary) return false
        if (buttonTertiaryHover != other.buttonTertiaryHover) return false
        if (buttonTertiaryActive != other.buttonTertiaryActive) return false
        if (buttonDangerPrimary != other.buttonDangerPrimary) return false
        if (buttonDangerSecondary != other.buttonDangerSecondary) return false
        if (buttonDangerHover != other.buttonDangerHover) return false
        if (buttonDangerActive != other.buttonDangerActive) return false
        if (buttonSeparator != other.buttonSeparator) return false
        if (buttonDisabled != other.buttonDisabled) return false
        if (supportError != other.supportError) return false
        if (supportSuccess != other.supportSuccess) return false
        if (supportWarning != other.supportWarning) return false
        if (supportInfo != other.supportInfo) return false
        if (supportErrorInverse != other.supportErrorInverse) return false
        if (supportSuccessInverse != other.supportSuccessInverse) return false
        if (supportWarningInverse != other.supportWarningInverse) return false
        if (supportInfoInverse != other.supportInfoInverse) return false
        if (focus != other.focus) return false
        if (focusInset != other.focusInset) return false
        if (focusInverse != other.focusInverse) return false
        if (interactive != other.interactive) return false
        if (highlight != other.highlight) return false
        if (toggleOff != other.toggleOff) return false
        if (overlay != other.overlay) return false
        if (skeletonElement != other.skeletonElement) return false
        if (skeletonBackground != other.skeletonBackground) return false
        if (tagColors != other.tagColors) return false

        return true
    }

    @Suppress("LongMethod")
    override fun hashCode(): Int {
        var result = background.hashCode()
        result = 31 * result + backgroundHover.hashCode()
        result = 31 * result + backgroundActive.hashCode()
        result = 31 * result + backgroundSelected.hashCode()
        result = 31 * result + backgroundSelectedHover.hashCode()
        result = 31 * result + backgroundInverse.hashCode()
        result = 31 * result + backgroundInverseHover.hashCode()
        result = 31 * result + backgroundBrand.hashCode()
        result = 31 * result + layer01.hashCode()
        result = 31 * result + layer02.hashCode()
        result = 31 * result + layer03.hashCode()
        result = 31 * result + layerHover01.hashCode()
        result = 31 * result + layerHover02.hashCode()
        result = 31 * result + layerHover03.hashCode()
        result = 31 * result + layerActive01.hashCode()
        result = 31 * result + layerActive02.hashCode()
        result = 31 * result + layerActive03.hashCode()
        result = 31 * result + layerSelected01.hashCode()
        result = 31 * result + layerSelected02.hashCode()
        result = 31 * result + layerSelected03.hashCode()
        result = 31 * result + layerSelectedHover01.hashCode()
        result = 31 * result + layerSelectedHover02.hashCode()
        result = 31 * result + layerSelectedHover03.hashCode()
        result = 31 * result + layerSelectedInverse.hashCode()
        result = 31 * result + layerSelectedDisabled.hashCode()
        result = 31 * result + layerAccent01.hashCode()
        result = 31 * result + layerAccent02.hashCode()
        result = 31 * result + layerAccent03.hashCode()
        result = 31 * result + layerAccentHover01.hashCode()
        result = 31 * result + layerAccentHover02.hashCode()
        result = 31 * result + layerAccentHover03.hashCode()
        result = 31 * result + layerAccentActive01.hashCode()
        result = 31 * result + layerAccentActive02.hashCode()
        result = 31 * result + layerAccentActive03.hashCode()
        result = 31 * result + field01.hashCode()
        result = 31 * result + field02.hashCode()
        result = 31 * result + field03.hashCode()
        result = 31 * result + fieldHover01.hashCode()
        result = 31 * result + fieldHover02.hashCode()
        result = 31 * result + fieldHover03.hashCode()
        result = 31 * result + borderInteractive.hashCode()
        result = 31 * result + borderSubtle00.hashCode()
        result = 31 * result + borderSubtle01.hashCode()
        result = 31 * result + borderSubtle02.hashCode()
        result = 31 * result + borderSubtle03.hashCode()
        result = 31 * result + borderSubtleSelected01.hashCode()
        result = 31 * result + borderSubtleSelected02.hashCode()
        result = 31 * result + borderSubtleSelected03.hashCode()
        result = 31 * result + borderStrong01.hashCode()
        result = 31 * result + borderStrong02.hashCode()
        result = 31 * result + borderStrong03.hashCode()
        result = 31 * result + borderTile01.hashCode()
        result = 31 * result + borderTile02.hashCode()
        result = 31 * result + borderTile03.hashCode()
        result = 31 * result + borderInverse.hashCode()
        result = 31 * result + borderDisabled.hashCode()
        result = 31 * result + textPrimary.hashCode()
        result = 31 * result + textSecondary.hashCode()
        result = 31 * result + textPlaceholder.hashCode()
        result = 31 * result + textOnColor.hashCode()
        result = 31 * result + textOnColorDisabled.hashCode()
        result = 31 * result + textHelper.hashCode()
        result = 31 * result + textError.hashCode()
        result = 31 * result + textInverse.hashCode()
        result = 31 * result + textDisabled.hashCode()
        result = 31 * result + linkPrimary.hashCode()
        result = 31 * result + linkPrimaryHover.hashCode()
        result = 31 * result + linkSecondary.hashCode()
        result = 31 * result + linkInverse.hashCode()
        result = 31 * result + linkVisited.hashCode()
        result = 31 * result + iconPrimary.hashCode()
        result = 31 * result + iconSecondary.hashCode()
        result = 31 * result + iconOnColor.hashCode()
        result = 31 * result + iconOnColorDisabled.hashCode()
        result = 31 * result + iconInteractive.hashCode()
        result = 31 * result + iconInverse.hashCode()
        result = 31 * result + iconDisabled.hashCode()
        result = 31 * result + buttonPrimary.hashCode()
        result = 31 * result + buttonPrimaryHover.hashCode()
        result = 31 * result + buttonPrimaryActive.hashCode()
        result = 31 * result + buttonSecondary.hashCode()
        result = 31 * result + buttonSecondaryHover.hashCode()
        result = 31 * result + buttonSecondaryActive.hashCode()
        result = 31 * result + buttonTertiary.hashCode()
        result = 31 * result + buttonTertiaryHover.hashCode()
        result = 31 * result + buttonTertiaryActive.hashCode()
        result = 31 * result + buttonDangerPrimary.hashCode()
        result = 31 * result + buttonDangerSecondary.hashCode()
        result = 31 * result + buttonDangerHover.hashCode()
        result = 31 * result + buttonDangerActive.hashCode()
        result = 31 * result + buttonSeparator.hashCode()
        result = 31 * result + buttonDisabled.hashCode()
        result = 31 * result + supportError.hashCode()
        result = 31 * result + supportSuccess.hashCode()
        result = 31 * result + supportWarning.hashCode()
        result = 31 * result + supportInfo.hashCode()
        result = 31 * result + supportErrorInverse.hashCode()
        result = 31 * result + supportSuccessInverse.hashCode()
        result = 31 * result + supportWarningInverse.hashCode()
        result = 31 * result + supportInfoInverse.hashCode()
        result = 31 * result + focus.hashCode()
        result = 31 * result + focusInset.hashCode()
        result = 31 * result + focusInverse.hashCode()
        result = 31 * result + interactive.hashCode()
        result = 31 * result + highlight.hashCode()
        result = 31 * result + toggleOff.hashCode()
        result = 31 * result + overlay.hashCode()
        result = 31 * result + skeletonElement.hashCode()
        result = 31 * result + skeletonBackground.hashCode()
        result = 31 * result + tagColors.hashCode()
        return result
    }
}
