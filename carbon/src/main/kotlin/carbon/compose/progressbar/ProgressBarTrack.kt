package carbon.compose.progressbar

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import carbon.compose.Carbon
import carbon.compose.foundation.spacing.SpacingScale

@Composable
internal fun ProgressBarTrack(
    size: ProgressBarSize,
    value: Float,
    modifier: Modifier = Modifier,
    state: ProgressBarState = ProgressBarState.Active,
    colors: ProgressBarColors = ProgressBarColors.colors()
) {
    val theme = Carbon.theme
    val trackColor by colors.trackColor(state)

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(
                when (size) {
                    ProgressBarSize.Big -> SpacingScale.spacing03
                    ProgressBarSize.Small -> SpacingScale.spacing02
                }
            )
    ) {
        drawRect(color = theme.borderSubtle00)
        drawTrack(
            trackHead = if (state == ProgressBarState.Active) value else 1f,
            trackTail = 0f,
            color = trackColor
        )
    }
}

private const val IndeterminateDurationAnimation = 1400
private val IndeterminateTrackDuration = (IndeterminateDurationAnimation * .75f * .25f).toInt()
private val IndeterminateAnimationDelay = IndeterminateTrackDuration

@Composable
internal fun IndeterminateProgressBarTrack(
    size: ProgressBarSize,
    modifier: Modifier = Modifier
) {
    val theme = Carbon.theme
    val intederminateInfiniteTransition = rememberInfiniteTransition()

    val trackHead by intederminateInfiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = IndeterminateDurationAnimation
                0f at 0 + IndeterminateAnimationDelay
                1f at IndeterminateDurationAnimation - IndeterminateTrackDuration
            }
        ),
        label = "TrackHead"
    )

    val trackTail by intederminateInfiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = IndeterminateDurationAnimation
                0f at IndeterminateAnimationDelay + IndeterminateTrackDuration
                1f at IndeterminateDurationAnimation
            }
        ),
        label = "TrackTail"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(
                when (size) {
                    ProgressBarSize.Big -> SpacingScale.spacing03
                    ProgressBarSize.Small -> SpacingScale.spacing02
                }
            )
    ) {
        drawRect(color = theme.borderSubtle00)
        drawTrack(
            trackHead = trackHead,
            trackTail = trackTail,
            color = theme.borderInteractive
        )
    }
}

private fun DrawScope.drawTrack(
    trackHead: Float,
    trackTail: Float,
    color: Color
) {
    drawLine(
        color = color,
        start = Offset(trackTail * this.size.width, this.size.height / 2),
        end = Offset(trackHead * this.size.width, this.size.height / 2),
        strokeWidth = this.size.height
    )
}
