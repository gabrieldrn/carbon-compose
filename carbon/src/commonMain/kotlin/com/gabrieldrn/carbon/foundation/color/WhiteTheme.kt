// ----------------------------------
// /!\ Generated code. Do not modify.
// ----------------------------------
@file:Suppress("TopLevelPropertyNaming", "TrailingWhitespace")

package com.gabrieldrn.carbon.foundation.color

import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.foundation.color.ai.WhiteAiColors
import com.gabrieldrn.carbon.foundation.color.button.WhiteButtonColors
import com.gabrieldrn.carbon.foundation.color.chat.WhiteChatColors
import com.gabrieldrn.carbon.foundation.color.notification.WhiteNotificationColors
import com.gabrieldrn.carbon.foundation.color.tag.WhiteTagColors
import kotlin.Suppress

/**
 * One of the default _light_ themes available in Carbon.
 *
 * This theme uses White as the global background color and is layered first with components using
 * Gray 10 backgrounds.
 *
 * The second layer uses White and the third layer used Gray 10.
 */
public val WhiteTheme: Theme = 
    Theme(
        background = Color(0xFFFFFFFF),
        backgroundActive = Color(0x7F8D8D8D),
        backgroundBrand = Color(0xFF0F62FE),
        backgroundHover = Color(0x1E8D8D8D),
        backgroundInverse = Color(0xFF393939),
        backgroundInverseHover = Color(0xFF474747),
        backgroundSelected = Color(0x338D8D8D),
        backgroundSelectedHover = Color(0x518D8D8D),
        borderDisabled = Color(0xFFC6C6C6),
        borderInteractive = Color(0xFF0F62FE),
        borderInverse = Color(0xFF161616),
        borderStrong01 = Color(0xFF8D8D8D),
        borderStrong02 = Color(0xFF8D8D8D),
        borderStrong03 = Color(0xFF8D8D8D),
        borderSubtle00 = Color(0xFFE0E0E0),
        borderSubtle01 = Color(0xFFC6C6C6),
        borderSubtle02 = Color(0xFFE0E0E0),
        borderSubtle03 = Color(0xFFC6C6C6),
        borderSubtleSelected01 = Color(0xFFC6C6C6),
        borderSubtleSelected02 = Color(0xFFC6C6C6),
        borderSubtleSelected03 = Color(0xFFC6C6C6),
        borderTile01 = Color(0xFFC6C6C6),
        borderTile02 = Color(0xFFA8A8A8),
        borderTile03 = Color(0xFFC6C6C6),
        field01 = Color(0xFFF4F4F4),
        field02 = Color(0xFFFFFFFF),
        field03 = Color(0xFFF4F4F4),
        fieldHover01 = Color(0xFFE8E8E8),
        fieldHover02 = Color(0xFFE8E8E8),
        fieldHover03 = Color(0xFFE8E8E8),
        focus = Color(0xFF0F62FE),
        focusInset = Color(0xFFFFFFFF),
        focusInverse = Color(0xFFFFFFFF),
        highlight = Color(0xFFD0E2FF),
        iconDisabled = Color(0x3F161616),
        iconInteractive = Color(0xFF0F62FE),
        iconInverse = Color(0xFFFFFFFF),
        iconOnColor = Color(0xFFFFFFFF),
        iconOnColorDisabled = Color(0xFF8D8D8D),
        iconPrimary = Color(0xFF161616),
        iconSecondary = Color(0xFF525252),
        interactive = Color(0xFF0F62FE),
        layer01 = Color(0xFFF4F4F4),
        layer02 = Color(0xFFFFFFFF),
        layer03 = Color(0xFFF4F4F4),
        layerAccent01 = Color(0xFFE0E0E0),
        layerAccent02 = Color(0xFFE0E0E0),
        layerAccent03 = Color(0xFFE0E0E0),
        layerAccentActive01 = Color(0xFFA8A8A8),
        layerAccentActive02 = Color(0xFFA8A8A8),
        layerAccentActive03 = Color(0xFFA8A8A8),
        layerAccentHover01 = Color(0xFFD1D1D1),
        layerAccentHover02 = Color(0xFFD1D1D1),
        layerAccentHover03 = Color(0xFFD1D1D1),
        layerActive01 = Color(0xFFC6C6C6),
        layerActive02 = Color(0xFFC6C6C6),
        layerActive03 = Color(0xFFC6C6C6),
        layerHover01 = Color(0xFFE8E8E8),
        layerHover02 = Color(0xFFE8E8E8),
        layerHover03 = Color(0xFFE8E8E8),
        layerSelected01 = Color(0xFFE0E0E0),
        layerSelected02 = Color(0xFFE0E0E0),
        layerSelected03 = Color(0xFFE0E0E0),
        layerSelectedDisabled = Color(0xFF8D8D8D),
        layerSelectedHover01 = Color(0xFFD1D1D1),
        layerSelectedHover02 = Color(0xFFD1D1D1),
        layerSelectedHover03 = Color(0xFFD1D1D1),
        layerSelectedInverse = Color(0xFF161616),
        linkInverse = Color(0xFF78A9FF),
        linkInverseActive = Color(0xFFF4F4F4),
        linkInverseHover = Color(0xFFA6C8FF),
        linkInverseVisited = Color(0xFFBE95FF),
        linkPrimary = Color(0xFF0F62FE),
        linkPrimaryHover = Color(0xFF0043CE),
        linkSecondary = Color(0xFF0043CE),
        linkVisited = Color(0xFF8A3FFC),
        overlay = Color(0x7F161616),
        shadow = Color(0x4C000000),
        skeletonBackground = Color(0xFFE8E8E8),
        skeletonElement = Color(0xFFC6C6C6),
        supportCautionMajor = Color(0xFFFF832B),
        supportCautionMinor = Color(0xFFF1C21B),
        supportCautionUndefined = Color(0xFF8A3FFC),
        supportError = Color(0xFFDA1E28),
        supportErrorInverse = Color(0xFFFA4D56),
        supportInfo = Color(0xFF0043CE),
        supportInfoInverse = Color(0xFF4589FF),
        supportSuccess = Color(0xFF24A148),
        supportSuccessInverse = Color(0xFF42BE65),
        supportWarning = Color(0xFFF1C21B),
        supportWarningInverse = Color(0xFFF1C21B),
        textDisabled = Color(0x3F161616),
        textError = Color(0xFFDA1E28),
        textHelper = Color(0xFF6F6F6F),
        textInverse = Color(0xFFFFFFFF),
        textOnColor = Color(0xFFFFFFFF),
        textOnColorDisabled = Color(0xFF8D8D8D),
        textPlaceholder = Color(0x66161616),
        textPrimary = Color(0xFF161616),
        textSecondary = Color(0xFF525252),
        toggleOff = Color(0xFF8D8D8D),
        aiColors = WhiteAiColors,
        buttonColors = WhiteButtonColors,
        chatColors = WhiteChatColors,
        notificationColors = WhiteNotificationColors,
        tagColors = WhiteTagColors
    )
