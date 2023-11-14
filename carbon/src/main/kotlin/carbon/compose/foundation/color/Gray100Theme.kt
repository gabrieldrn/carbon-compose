package carbon.compose.foundation.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * One of the default _dark_ themes available in Carbon. This theme uses Grey 100 as the global
 * background color.
 */
@Immutable
public object Gray100Theme : Theme {
    override val background: Color = Color(0xFF161616)
    override val backgroundHover: Color = Color(0x298D8D8D)
    override val backgroundActive: Color = Color(0x668D8D8D)
    override val backgroundSelected: Color = Color(0x3D8D8D8D)
    override val backgroundSelectedHover: Color = Color(0x528D8D8D)
    override val backgroundInverse: Color = Color(0xFF393939)
    override val backgroundInverseHover: Color = Color(0xFFE5E5E5)
    override val backgroundBrand: Color = Color(0xFF0F62FE)

    override val layer01: Color = Color(0xFF262626)
    override val layer02: Color = Color(0xFF393939)
    override val layer03: Color = Color(0xFF525252)
    override val layerHover01: Color = Color(0xFF333333)
    override val layerHover02: Color = Color(0xFF474747)
    override val layerHover03: Color = Color(0xFF636363)
    override val layerActive01: Color = Color(0xFF525252)
    override val layerActive02: Color = Color(0xFF6F6F6F)
    override val layerActive03: Color = Color(0xFF8D8D8D)
    override val layerSelected01: Color = Color(0xFF393939)
    override val layerSelected02: Color = Color(0xFF525252)
    override val layerSelected03: Color = Color(0xFF6F6F6F)
    override val layerSelectedHover01: Color = Color(0xFF4C4C4C)
    override val layerSelectedHover02: Color = Color(0xFF656565)
    override val layerSelectedHover03: Color = Color(0xFF5E5E5E)
    override val layerSelectedInverse: Color = Color(0xFFF4F4F4)
    override val layerSelectedDisabled: Color = Color(0xFF6F6F6F)

    override val layerAccent01: Color = Color(0xFF393939)
    override val layerAccent02: Color = Color(0xFF525252)
    override val layerAccent03: Color = Color(0xFF6F6F6F)
    override val layerAccentHover01: Color = Color(0xFF4C4C4C)
    override val layerAccentHover02: Color = Color(0xFF656565)
    override val layerAccentHover03: Color = Color(0xFF5E5E5E)
    override val layerAccentActive01: Color = Color(0xFF525252)
    override val layerAccentActive02: Color = Color(0xFF8D8D8D)
    override val layerAccentActive03: Color = Color(0xFF393939)

    override val field01: Color = Color(0xFF262626)
    override val field02: Color = Color(0xFF393939)
    override val field03: Color = Color(0xFF525252)
    override val fieldHover01: Color = Color(0xFF333333)
    override val fieldHover02: Color = Color(0xFF474747)
    override val fieldHover03: Color = Color(0xFF636363)

    override val borderInteractive: Color = Color(0xFF4589FF)
    override val borderSubtle00: Color = Color(0xFF393939)
    override val borderSubtle01: Color = Color(0xFF393939)
    override val borderSubtle02: Color = Color(0xFF525252)
    override val borderSubtle03: Color = Color(0xFF6F6F6F)
    override val borderSubtleSelected01: Color = Color(0xFF525252)
    override val borderSubtleSelected02: Color = Color(0xFF6F6F6F)
    override val borderSubtleSelected03: Color = Color(0xFF8D8D8D)
    override val borderStrong01: Color = Color(0xFF6F6F6F)
    override val borderStrong02: Color = Color(0xFF8D8D8D)
    override val borderStrong03: Color = Color(0xFFA8A8A8)
    override val borderTile01: Color = Color(0xFF525252)
    override val borderTile02: Color = Color(0xFF6F6F6F)
    override val borderTile03: Color = Color(0xFF8D8D8D)
    override val borderInverse: Color = Color(0xFFF4F4F4)
    override val borderDisabled: Color = Color(0xFF8D8D8D)

    override val textPrimary: Color = Color(0xFFF4F4F4)
    override val textSecondary: Color = Color(0xFFC6C6C6)
    override val textPlaceholder: Color = Color(0xFF6F6F6F)
    override val textOnColor: Color = Color.White
    override val textOnColorDisabled: Color = Color(0x40FFFFFF)
    override val textHelper: Color = Color(0xFF8D8D8D)
    override val textError: Color = Color(0xFFFF8389)
    override val textInverse: Color = Color(0xFF161616)
    override val textDisabled: Color = Color(0x40F4F4F4)

    override val linkPrimary: Color = Color(0xFF78A9FF)
    override val linkPrimaryHover: Color = Color(0xFFA6C8FF)
    override val linkSecondary: Color = Color(0xFFA6C8FF)
    override val linkInverse: Color = Color(0xFF0F62FE)
    override val linkVisited: Color = Color(0xFFBE95FF)

    override val iconPrimary: Color = Color(0xFFF4F4F4)
    override val iconSecondary: Color = Color(0xFFC6C6C6)
    override val iconOnColor: Color = Color.White
    override val iconOnColorDisabled: Color = Color(0x40FFFFFF)
    override val iconInteractive: Color = Color(0xFFFFFFFF)
    override val iconInverse: Color = Color(0xFF161616)
    override val iconDisabled: Color = Color(0x40F4F4F4)

    override val buttonPrimary: Color = Color(0xFF0F62FE)
    override val buttonPrimaryHover: Color = Color(0xFF0353E9)
    override val buttonPrimaryActive: Color = Color(0xFF002D9C)
    override val buttonSecondary: Color = Color(0xFF6F6F6F)
    override val buttonSecondaryHover: Color = Color(0xFF606060)
    override val buttonSecondaryActive: Color = Color(0xFF393939)
    override val buttonTertiary: Color = Color.White
    override val buttonTertiaryHover: Color = Color(0xFFF4F4F4)
    override val buttonTertiaryActive: Color = Color(0xFFC6C6C6)
    override val buttonDangerPrimary: Color = Color(0xFFDA1E28)
    override val buttonDangerSecondary: Color = Color(0xFFFA4D56)
    override val buttonDangerHover: Color = Color(0xFFBA1B23)
    override val buttonDangerActive: Color = Color(0xFF750E13)
    override val buttonSeparator: Color = Color(0xFF161616)
    override val buttonDisabled: Color = Color(0xFF525252)

    override val supportError: Color = Color(0xFFFA4D56)
    override val supportSuccess: Color = Color(0xFF42BE65)
    override val supportWarning: Color = Color(0xFFF1C21B)
    override val supportInfo: Color = Color(0xFF4589FF)
    override val supportErrorInverse: Color = Color(0xFFDA1E28)
    override val supportSuccessInverse: Color = Color(0xFF24A148)
    override val supportWarningInverse: Color = Color(0xFFF1C21B)
    override val supportInfoInverse: Color = Color(0xFF0043CE)

    override val focus: Color = Color.White
    override val focusInset: Color = Color(0xFF161616)
    override val focusInverse: Color = Color(0xFF0F62FE)

    override val interactive: Color = Color(0xFF4589FF)
    override val highlight: Color = Color(0xFF001D6C)
    override val toggleOff: Color = Color(0xFF6F6F6F)
    override val overlay: Color = Color(0xB3161616)
    override val skeletonElement: Color = Color(0xFF525252)
    override val skeletonBackground: Color = Color(0xFF353535)
}
