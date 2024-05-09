package carbon.compose.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.color.LocalCarbonTheme

private const val SPINNER_RADIUS = 360f * (1 - .19f)
private val SPINNER_SIZE = 88.dp
private val SPINNER_STROKE_WIDTH = 10.dp

/**
 * # Loading
 *
 * Loading spinners are used when retrieving data or performing slow computations, and help to
 * notify users that loading is underway.
 *
 * (From [Dropdown documentation](https://carbondesignsystem.com/components/loading/usage))
 */
@Composable
public fun Loading(modifier: Modifier = Modifier) {
    val loadingColor = LocalCarbonTheme.current.interactive

    val infiniteTransition = rememberInfiniteTransition()
    val rotationZ by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 690, easing = LinearEasing),
        )
    )

    Canvas(modifier = Modifier.requiredSize(SPINNER_SIZE).then(modifier)) {
        inset(SPINNER_STROKE_WIDTH.toPx() * .5f) {
            rotate(rotationZ) {
                drawArc(
                    color = loadingColor,
                    startAngle = 0f,
                    sweepAngle = SPINNER_RADIUS,
                    useCenter = false,
                    style = Stroke(SPINNER_STROKE_WIDTH.toPx())
                )
            }
        }
    }
}
