package carbon.compose.foundation.text

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import carbon.compose.R

public val ibmPlexSansFamily: FontFamily = FontFamily(
    Font(R.font.ibmplexsans_light, FontWeight.Light),
    Font(R.font.ibmplexsans_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.ibmplexsans_regular, FontWeight.Normal),
    Font(R.font.ibmplexsans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.ibmplexsans_semibold, FontWeight.SemiBold),
    Font(R.font.ibmplexsans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

public val ibmPlexSerifFamily: FontFamily = FontFamily(
    Font(R.font.ibmplexserif_light, FontWeight.Light),
    Font(R.font.ibmplexserif_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.ibmplexserif_regular, FontWeight.Normal),
    Font(R.font.ibmplexserif_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.ibmplexserif_semibold, FontWeight.SemiBold),
    Font(R.font.ibmplexserif_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

public val ibmPlexMonoFamily: FontFamily = FontFamily(
    Font(R.font.ibmplexmono_light, FontWeight.Light),
    Font(R.font.ibmplexmono_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.ibmplexmono_regular, FontWeight.Normal),
    Font(R.font.ibmplexmono_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.ibmplexmono_semibold, FontWeight.SemiBold),
    Font(R.font.ibmplexmono_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

@Suppress("UndocumentedPublicProperty")
public object CarbonTypography {
    // region Utility

    public val code01: TextStyle = TextStyle(
        fontFamily = ibmPlexMonoFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = .32.sp,
    )
    public val code02: TextStyle = TextStyle(
        fontFamily = ibmPlexMonoFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = .32.sp,
    )
    public val label01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = .32.sp,
    )
    public val label02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = .16.sp,
    )
    public val helperText01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = .32.sp,
    )
    public val helperText02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = .16.sp,
    )
    public val legal01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = .32.sp,
    )
    public val legal02: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = .16.sp,
    )

    // endregion

    // region Body

    public val bodyCompact01: TextStyle = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        letterSpacing = .16.sp,
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
        letterSpacing = .16.sp,
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
        letterSpacing = .16.sp,
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
        letterSpacing = .16.sp,
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
