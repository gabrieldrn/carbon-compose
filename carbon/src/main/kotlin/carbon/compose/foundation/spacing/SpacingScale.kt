package carbon.compose.foundation.spacing

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The Carbon spacing scale complements the 2x Grid and typography scale by using multiples of two,
 * four, and eight. It includes both small increments needed to create appropriate spatial
 * relationships for detail-level designs as well as larger increments used to control the density
 * of a design. Each level of the spacing scale has its own token. Spacing tokens can be used inside
 * of components for building and between components for layout spacing.
 *
 * See [Spacing guidelines](https://carbondesignsystem.com/guidelines/spacing/overview/) for more
 * information.
 */
@Suppress("UndocumentedPublicProperty")
public object SpacingScale {
    public val spacing01: Dp = 2.dp
    public val spacing02: Dp = 4.dp
    public val spacing03: Dp = 8.dp
    public val spacing04: Dp = 12.dp
    public val spacing05: Dp = 16.dp
    public val spacing06: Dp = 24.dp
    public val spacing07: Dp = 32.dp
    public val spacing08: Dp = 40.dp
    public val spacing09: Dp = 48.dp
    public val spacing10: Dp = 64.dp
    public val spacing11: Dp = 80.dp
    public val spacing12: Dp = 96.dp
    public val spacing13: Dp = 160.dp
}
