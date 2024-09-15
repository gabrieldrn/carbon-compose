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

package com.gabrieldrn.carbon.foundation.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import com.gabrieldrn.carbon.Res
import com.gabrieldrn.carbon.ibmplexmono_italic
import com.gabrieldrn.carbon.ibmplexmono_light
import com.gabrieldrn.carbon.ibmplexmono_lightitalic
import com.gabrieldrn.carbon.ibmplexmono_regular
import com.gabrieldrn.carbon.ibmplexmono_semibold
import com.gabrieldrn.carbon.ibmplexmono_semibolditalic
import com.gabrieldrn.carbon.ibmplexsans_italic
import com.gabrieldrn.carbon.ibmplexsans_light
import com.gabrieldrn.carbon.ibmplexsans_lightitalic
import com.gabrieldrn.carbon.ibmplexsans_regular
import com.gabrieldrn.carbon.ibmplexsans_semibold
import com.gabrieldrn.carbon.ibmplexsans_semibolditalic
import com.gabrieldrn.carbon.ibmplexserif_italic
import com.gabrieldrn.carbon.ibmplexserif_light
import com.gabrieldrn.carbon.ibmplexserif_lightitalic
import com.gabrieldrn.carbon.ibmplexserif_regular
import com.gabrieldrn.carbon.ibmplexserif_semibold
import com.gabrieldrn.carbon.ibmplexserif_semibolditalic
import org.jetbrains.compose.resources.Font

/**
 * A [staticCompositionLocalOf] that provides the current [CarbonTypography].
 */
public val LocalCarbonTypography: ProvidableCompositionLocal<CarbonTypography> =
    staticCompositionLocalOf {
        Logger.w(
            "No Typography provided, please make sure to wrap your UI " +
                "with the CarbonDesignSystem composable."
        )
        CarbonTypography(
            FontFamily.SansSerif,
            FontFamily.Serif,
            FontFamily.Monospace
        )
    }

/**
 * Builds and returns a [FontFamily] of the IBM Plex **sans serif** font.
 */
@Composable
public fun getIBMPlexSansFamily(): FontFamily = FontFamily(
    Font(Res.font.ibmplexsans_light, FontWeight.Light),
    Font(Res.font.ibmplexsans_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.ibmplexsans_regular, FontWeight.Normal),
    Font(Res.font.ibmplexsans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.ibmplexsans_semibold, FontWeight.SemiBold),
    Font(Res.font.ibmplexsans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

/**
 * Builds and returns a [FontFamily] of the IBM Plex **serif** font.
 */
@Composable
public fun getIBMPlexSerifFamily(): FontFamily = FontFamily(
    Font(Res.font.ibmplexserif_light, FontWeight.Light),
    Font(Res.font.ibmplexserif_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.ibmplexserif_regular, FontWeight.Normal),
    Font(Res.font.ibmplexserif_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.ibmplexserif_semibold, FontWeight.SemiBold),
    Font(Res.font.ibmplexserif_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

/**
 * Builds and returns a [FontFamily] of the IBM Plex **monospace** font.
 */
@Composable
public fun getIBMPlexMonoFamily(): FontFamily = FontFamily(
    Font(Res.font.ibmplexmono_light, FontWeight.Light),
    Font(Res.font.ibmplexmono_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.ibmplexmono_regular, FontWeight.Normal),
    Font(Res.font.ibmplexmono_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.ibmplexmono_semibold, FontWeight.SemiBold),
    Font(Res.font.ibmplexmono_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

/**
 * Carbon typography type sets.
 *
 * See [Typography type sets](https://carbondesignsystem.com/guidelines/typography/type-sets) for
 * more information.
 *
 * @param ibmPlexSansFamily The IBM Plex **sans serif** font family.
 * @param ibmPlexSerifFamily The IBM Plex **serif** font family.
 * @param ibmPlexMonoFamily The IBM Plex **monospace** font family.
 */
@Suppress("UndocumentedPublicProperty")
public data class CarbonTypography(
    public val ibmPlexSansFamily: FontFamily,
    public val ibmPlexSerifFamily: FontFamily,
    public val ibmPlexMonoFamily: FontFamily,
) {
    // region Utility

    public val code01: TextStyle = TextStyle(
        fontFamily = ibmPlexMonoFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = .32f.sp,
    )
    public val code02: TextStyle = TextStyle(
        fontFamily = ibmPlexMonoFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = .32f.sp,
    )
    public val label01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = .32f.sp,
    )
    public val label02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = .16f.sp,
    )
    public val helperText01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = .32f.sp,
    )
    public val helperText02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = .16f.sp,
    )
    public val legal01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = .32f.sp,
    )
    public val legal02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = .16f.sp,
    )

    // endregion

    // region Body

    public val bodyCompact01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = .16f.sp,
        baselineShift = BaselineShift(.16f),
    )
    public val bodyCompact02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    )
    public val body01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = .16f.sp,
    )
    public val body02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    )

    // endregion

    // region Heading

    public val headingCompact01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = .16f.sp,
    )
    public val headingCompact02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.SemiBold,
    )
    public val heading01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = .16f.sp,
    )
    public val heading02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.SemiBold,
    )
    public val heading03: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.SemiBold,
    )
    public val heading04: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    )
    public val heading05: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 32.sp,
        lineHeight = 40.sp,
    )
    public val heading06: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 42.sp,
        lineHeight = 50.sp,
        fontWeight = FontWeight.Light,
    )
    public val heading07: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 54.sp,
        lineHeight = 64.sp,
        fontWeight = FontWeight.Light,
    )

    // endregion

    // region Display
    // Adapted from Fluid Display styles, fixed to Sm breakpoint.

    public val paragraph01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Light,
    )
    public val quotation01: TextStyle = TextStyle(
        fontFamily = ibmPlexSerifFamily,
        fontSize = 20.sp,
        lineHeight = 26.sp,
    )
    public val quotation02: TextStyle = TextStyle(
        fontFamily = ibmPlexSerifFamily,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Light,
    )
    public val display01: TextStyle = TextStyle(
        fontFamily = ibmPlexSerifFamily,
        fontSize = 42.sp,
        lineHeight = 50.sp,
        fontWeight = FontWeight.Light,
    )
    public val display02: TextStyle = TextStyle(
        fontFamily = ibmPlexSerifFamily,
        fontSize = 42.sp,
        lineHeight = 50.sp,
        fontWeight = FontWeight.SemiBold,
    )

    // endregion
}
