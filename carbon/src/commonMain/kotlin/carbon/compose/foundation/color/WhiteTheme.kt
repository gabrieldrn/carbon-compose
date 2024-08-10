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

package carbon.compose.foundation.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * One of the default _light_ themes available in Carbon. This theme uses white as the global
 * background color.
 */
@Immutable
public object WhiteTheme : Theme() {
    override val background: Color = Color.White
    override val backgroundHover: Color = Color(0x1F8D8D8D)
    override val backgroundActive: Color = Color(0x808D8D8D)
    override val backgroundSelected: Color = Color(0x338D8D8D)
    override val backgroundSelectedHover: Color = Color(0x528D8D8D)
    override val backgroundInverse: Color = Color(0xFF393939)
    override val backgroundInverseHover: Color = Color(0xFF4C4C4C)
    override val backgroundBrand: Color = Color(0xFF0F62FE)

    override val layer01: Color = Color(0xFFF4F4F4)
    override val layer02: Color = Color(0xFFFFFFFF)
    override val layer03: Color = Color(0xFFF4F4F4)
    override val layerHover01: Color = Color(0xFFE8E8E8)
    override val layerHover02: Color = Color(0xFFE8E8E8)
    override val layerHover03: Color = Color(0xFFE8E8E8)
    override val layerActive01: Color = Color(0xFFC6C6C6)
    override val layerActive02: Color = Color(0xFFC6C6C6)
    override val layerActive03: Color = Color(0xFFC6C6C6)
    override val layerSelected01: Color = Color(0xFFE0E0E0)
    override val layerSelected02: Color = Color(0xFFE0E0E0)
    override val layerSelected03: Color = Color(0xFFE0E0E0)
    override val layerSelectedHover01: Color = Color(0xFFCACACA)
    override val layerSelectedHover02: Color = Color(0xFFCACACA)
    override val layerSelectedHover03: Color = Color(0xFFCACACA)
    override val layerSelectedInverse: Color = Color(0xFF161616)
    override val layerSelectedDisabled: Color = Color(0xFF8D8D8D)

    override val layerAccent01: Color = Color(0xFFE0E0E0)
    override val layerAccent02: Color = Color(0xFFE0E0E0)
    override val layerAccent03: Color = Color(0xFFE0E0E0)
    override val layerAccentHover01: Color = Color(0xFFCACACA)
    override val layerAccentHover02: Color = Color(0xFFCACACA)
    override val layerAccentHover03: Color = Color(0xFFCACACA)
    override val layerAccentActive01: Color = Color(0xFFA8A8A8)
    override val layerAccentActive02: Color = Color(0xFFA8A8A8)
    override val layerAccentActive03: Color = Color(0xFFA8A8A8)

    override val field01: Color = Color(0xFFF4F4F4)
    override val field02: Color = Color.White
    override val field03: Color = Color(0xFFF4F4F4)
    override val fieldHover01: Color = Color(0xFFE8E8E8)
    override val fieldHover02: Color = Color(0xFFE8E8E8)
    override val fieldHover03: Color = Color(0xFFE8E8E8)

    override val borderInteractive: Color = Color(0xFF0F62FE)
    override val borderSubtle00: Color = Color(0xFFE0E0E0)
    override val borderSubtle01: Color = Color(0xFFC6C6C6)
    override val borderSubtle02: Color = Color(0xFFE0E0E0)
    override val borderSubtle03: Color = Color(0xFFC6C6C6)
    override val borderSubtleSelected01: Color = Color(0xFFC6C6C6)
    override val borderSubtleSelected02: Color = Color(0xFFC6C6C6)
    override val borderSubtleSelected03: Color = Color(0xFFC6C6C6)
    override val borderStrong01: Color = Color(0xFF8D8D8D)
    override val borderStrong02: Color = Color(0xFF8D8D8D)
    override val borderStrong03: Color = Color(0xFF8D8D8D)
    override val borderTile01: Color = Color(0xFFC6C6C6)
    override val borderTile02: Color = Color(0xFFA8A8A8)
    override val borderTile03: Color = Color(0xFFC6C6C6)
    override val borderInverse: Color = Color(0xFF161616)
    override val borderDisabled: Color = Color(0xFFC6C6C6)

    override val textPrimary: Color = Color(0xFF161616)
    override val textSecondary: Color = Color(0xFF525252)
    override val textPlaceholder: Color = Color(0xFFA8A8A8)
    override val textOnColor: Color = Color.White
    override val textOnColorDisabled: Color = Color(0xFF8D8D8D)
    override val textHelper: Color = Color(0xFF6F6F6F)
    override val textError: Color = Color(0xFFDA1E28)
    override val textInverse: Color = Color.White
    override val textDisabled: Color = Color(0x40161616)

    override val linkPrimary: Color = Color(0xFF0F62FE)
    override val linkPrimaryHover: Color = Color(0xFF0043CE)
    override val linkSecondary: Color = Color(0xFF0043CE)
    override val linkInverse: Color = Color(0xFF78A9FF)
    override val linkVisited: Color = Color(0xFF8A3FFC)

    override val iconPrimary: Color = Color(0xFF161616)
    override val iconSecondary: Color = Color(0xFF525252)
    override val iconOnColor: Color = Color.White
    override val iconOnColorDisabled: Color = Color(0xFF8D8D8D)
    override val iconInteractive: Color = Color(0xFF0F62FE)
    override val iconInverse: Color = Color.White
    override val iconDisabled: Color = Color(0x40161616)

    override val buttonPrimary: Color = Color(0xFF0F62FE)
    override val buttonPrimaryHover: Color = Color(0xFF0353E9)
    override val buttonPrimaryActive: Color = Color(0xFF002D9C)
    override val buttonSecondary: Color = Color(0xFF393939)
    override val buttonSecondaryHover: Color = Color(0xFF4C4C4C)
    override val buttonSecondaryActive: Color = Color(0xFF6F6F6F)
    override val buttonTertiary: Color = Color(0xFF0F62FE)
    override val buttonTertiaryHover: Color = Color(0xFF0353E9)
    override val buttonTertiaryActive: Color = Color(0xFF002D9C)
    override val buttonDangerPrimary: Color = Color(0xFFDA1E28)
    override val buttonDangerSecondary: Color = Color(0xFFDA1E28)
    override val buttonDangerHover: Color = Color(0xFFBA1B23)
    override val buttonDangerActive: Color = Color(0xFF750E13)
    override val buttonSeparator: Color = Color(0xFFE0E0E0)
    override val buttonDisabled: Color = Color(0xFFC6C6C6)

    override val supportError: Color = Color(0xFFDA1E28)
    override val supportSuccess: Color = Color(0xFF24A148)
    override val supportWarning: Color = Color(0xFFF1C21B)
    override val supportInfo: Color = Color(0xFF0043CE)
    override val supportErrorInverse: Color = Color(0xFFFA4D56)
    override val supportSuccessInverse: Color = Color(0xFF42BE65)
    override val supportWarningInverse: Color = Color(0xFFF1C21B)
    override val supportInfoInverse: Color = Color(0xFF4589FF)

    override val focus: Color = Color(0xFF0F62FE)
    override val focusInset: Color = Color.White
    override val focusInverse: Color = Color.White

    override val interactive: Color = Color(0xFF0F62FE)
    override val highlight: Color = Color(0xFFD0E2FF)
    override val toggleOff: Color = Color(0xFF8D8D8D)
    override val overlay: Color = Color(0x80161616)
    override val skeletonElement: Color = Color(0xFFC6C6C6)
    override val skeletonBackground: Color = Color(0xFFE5E5E5)
}
