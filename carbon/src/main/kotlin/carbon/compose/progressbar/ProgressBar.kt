package carbon.compose.progressbar

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text

/**
 * # Progress Bar
 *
 * A progress bar provides feedback about the duration and progression of a process, such as a
 * download, file transfer, or installation, to indicate how long a user will be waiting.
 *
 * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
 *
 * @param value The progress value, between 0 and 1.
 * @param modifier The modifier to apply to this composable.
 * @param labelText The text label on top of the progress bar.
 * @param helperText The helper text below the progress bar.
 * @param indented Whether to indent the label and helper texts.
 * @param inlined When true, sets the label text inlined with the progress bar. In this case the
 * helper text is not displayed.
 * @param size The size type of the progress bar.
 */
@Composable
public fun ProgressBar(
    @FloatRange(from = 0.0, to = 1.0) value: Float,
    modifier: Modifier = Modifier,
    labelText: String? = null,
    helperText: String? = null,
    indented: Boolean = false,
    inlined: Boolean = false,
    size: ProgressBarSize = ProgressBarSize.Big,
) {
    if (inlined) {
        ProgressBarRowLayout(
            labelText = labelText,
            modifier = modifier,
            trackContent = {
                ProgressBarTrack(size = size, value = value)
            }
        )
    } else {
        ProgressBarColumnLayout(
            labelText = labelText,
            helperText = helperText,
            indented = indented,
            modifier = modifier,
            trackContent = {
                ProgressBarTrack(size = size, value = value)
            }
        )
    }
}

@Composable
private fun ProgressBarRowLayout(
    labelText: String?,
    modifier: Modifier = Modifier,
    trackContent: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (labelText != null) {
            LabelText(text = labelText)
        }
        Spacer(modifier = Modifier.width(SpacingScale.spacing05))
        trackContent()
    }
}

@Composable
private fun ProgressBarColumnLayout(
    labelText: String?,
    helperText: String?,
    indented: Boolean,
    modifier: Modifier = Modifier,
    trackContent: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        if (labelText != null) {
            LabelText(
                text = labelText,
                modifier = if (indented) {
                    Modifier.padding(start = SpacingScale.spacing05)
                } else {
                    Modifier
                }
            )
        }

        Spacer(modifier = Modifier.height(SpacingScale.spacing03))

        trackContent()

        if (helperText != null) {
            Text(
                text = helperText,
                style = CarbonTypography.helperText01,
                color = Carbon.theme.textHelper,
                modifier = Modifier.padding(
                    top = SpacingScale.spacing03,
                    end = if (indented) {
                        SpacingScale.spacing05
                    } else {
                        0.dp
                    }
                )
            )
        }
    }
}

@Composable
private fun LabelText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = CarbonTypography.label01,
        color = Carbon.theme.textPrimary,
        modifier = modifier
    )
}

@Composable
private fun ProgressBarTrack(
    size: ProgressBarSize,
    value: Float,
    modifier: Modifier = Modifier
) {
    val theme = Carbon.theme

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
            trackHead = value,
            trackTail = 0f,
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
