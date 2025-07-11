// ----------------------------------
// /!\ Generated code. Do not modify.
// ----------------------------------
@file:Suppress("TopLevelPropertyNaming", "TrailingWhitespace")

package com.gabrieldrn.carbon.foundation.color

import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.foundation.color.ai.Gray100AiColors
import com.gabrieldrn.carbon.foundation.color.button.Gray100ButtonColors
import com.gabrieldrn.carbon.foundation.color.chat.Gray100ChatColors
import com.gabrieldrn.carbon.foundation.color.notification.Gray100NotificationColors
import com.gabrieldrn.carbon.foundation.color.tag.Gray100TagColors
import kotlin.Suppress

/**
 * One of the default _light_ themes available in Carbon.
 *
 * This theme uses Gray 10 as the global background color and is layered first with components using White backgrounds.
 *
 * The second layer uses Gray 10 and the third layer used White.
 */
public val Gray100Theme: Theme = 
    Theme(
        background = Color(0xFF161616),
        backgroundActive = Color(0x668D8D8D),
        backgroundBrand = Color(0xFF0F62FE),
        backgroundHover = Color(0x288D8D8D),
        backgroundInverse = Color(0xFFF4F4F4),
        backgroundInverseHover = Color(0xFFE8E8E8),
        backgroundSelected = Color(0x3D8D8D8D),
        backgroundSelectedHover = Color(0x518D8D8D),
        borderDisabled = Color(0x7F8D8D8D),
        borderInteractive = Color(0xFF4589FF),
        borderInverse = Color(0xFFF4F4F4),
        borderStrong01 = Color(0xFF6F6F6F),
        borderStrong02 = Color(0xFF8D8D8D),
        borderStrong03 = Color(0xFFA8A8A8),
        borderSubtle00 = Color(0xFF393939),
        borderSubtle01 = Color(0xFF525252),
        borderSubtle02 = Color(0xFF6F6F6F),
        borderSubtle03 = Color(0xFF6F6F6F),
        borderSubtleSelected01 = Color(0xFF6F6F6F),
        borderSubtleSelected02 = Color(0xFF8D8D8D),
        borderSubtleSelected03 = Color(0xFF8D8D8D),
        borderTile01 = Color(0xFF525252),
        borderTile02 = Color(0xFF6F6F6F),
        borderTile03 = Color(0xFF8D8D8D),
        field01 = Color(0xFF262626),
        field02 = Color(0xFF393939),
        field03 = Color(0xFF525252),
        fieldHover01 = Color(0xFF333333),
        fieldHover02 = Color(0xFF474747),
        fieldHover03 = Color(0xFF636363),
        focus = Color(0xFFFFFFFF),
        focusInset = Color(0xFF161616),
        focusInverse = Color(0xFF0F62FE),
        highlight = Color(0xFF001D6C),
        iconDisabled = Color(0x3FF4F4F4),
        iconInteractive = Color(0xFFFFFFFF),
        iconInverse = Color(0xFF161616),
        iconOnColor = Color(0xFFFFFFFF),
        iconOnColorDisabled = Color(0x3FFFFFFF),
        iconPrimary = Color(0xFFF4F4F4),
        iconSecondary = Color(0xFFC6C6C6),
        interactive = Color(0xFF4589FF),
        layer01 = Color(0xFF262626),
        layer02 = Color(0xFF393939),
        layer03 = Color(0xFF525252),
        layerAccent01 = Color(0xFF393939),
        layerAccent02 = Color(0xFF525252),
        layerAccent03 = Color(0xFF6F6F6F),
        layerAccentActive01 = Color(0xFF6F6F6F),
        layerAccentActive02 = Color(0xFF8D8D8D),
        layerAccentActive03 = Color(0xFF393939),
        layerAccentHover01 = Color(0xFF474747),
        layerAccentHover02 = Color(0xFF636363),
        layerAccentHover03 = Color(0xFF5E5E5E),
        layerActive01 = Color(0xFF525252),
        layerActive02 = Color(0xFF6F6F6F),
        layerActive03 = Color(0xFF8D8D8D),
        layerBackground01 = Color(0xFF161616),
        layerBackground02 = Color(0xFF262626),
        layerBackground03 = Color(0xFF393939),
        layerHover01 = Color(0xFF333333),
        layerHover02 = Color(0xFF474747),
        layerHover03 = Color(0xFF636363),
        layerSelected01 = Color(0xFF393939),
        layerSelected02 = Color(0xFF525252),
        layerSelected03 = Color(0xFF6F6F6F),
        layerSelectedDisabled = Color(0xFFA8A8A8),
        layerSelectedHover01 = Color(0xFF474747),
        layerSelectedHover02 = Color(0xFF636363),
        layerSelectedHover03 = Color(0xFF5E5E5E),
        layerSelectedInverse = Color(0xFFF4F4F4),
        linkInverse = Color(0xFF0F62FE),
        linkInverseActive = Color(0xFF161616),
        linkInverseHover = Color(0xFF0043CE),
        linkInverseVisited = Color(0xFF8A3FFC),
        linkPrimary = Color(0xFF78A9FF),
        linkPrimaryHover = Color(0xFFA6C8FF),
        linkSecondary = Color(0xFFA6C8FF),
        linkVisited = Color(0xFFBE95FF),
        overlay = Color(0xA5000000),
        shadow = Color(0xCC000000),
        skeletonBackground = Color(0xFF292929),
        skeletonElement = Color(0xFF393939),
        supportCautionMajor = Color(0xFFFF832B),
        supportCautionMinor = Color(0xFFF1C21B),
        supportCautionUndefined = Color(0xFFA56EFF),
        supportError = Color(0xFFFA4D56),
        supportErrorInverse = Color(0xFFDA1E28),
        supportInfo = Color(0xFF4589FF),
        supportInfoInverse = Color(0xFF0043CE),
        supportSuccess = Color(0xFF42BE65),
        supportSuccessInverse = Color(0xFF24A148),
        supportWarning = Color(0xFFF1C21B),
        supportWarningInverse = Color(0xFFF1C21B),
        textDisabled = Color(0x3FF4F4F4),
        textError = Color(0xFFFF8389),
        textHelper = Color(0xFFA8A8A8),
        textInverse = Color(0xFF161616),
        textOnColor = Color(0xFFFFFFFF),
        textOnColorDisabled = Color(0x3FFFFFFF),
        textPlaceholder = Color(0x66F4F4F4),
        textPrimary = Color(0xFFF4F4F4),
        textSecondary = Color(0xFFC6C6C6),
        toggleOff = Color(0xFF6F6F6F),
        aiColors = Gray100AiColors,
        buttonColors = Gray100ButtonColors,
        chatColors = Gray100ChatColors,
        notificationColors = Gray100NotificationColors,
        tagColors = Gray100TagColors
    )
