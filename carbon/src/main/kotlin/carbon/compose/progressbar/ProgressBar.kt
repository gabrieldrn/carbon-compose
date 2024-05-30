package carbon.compose.progressbar

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
 * @param size The size type of the progress bar.
 */
@Composable
public fun ProgressBar(
    @FloatRange(from = 0.0, to = 1.0) value: Float,
    modifier: Modifier = Modifier,
//    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    labelText: String? = null,
    helperText: String? = null,
    indented: Boolean = false,
    size: ProgressBarSize = ProgressBarSize.Big
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

        ProgressBarTrack(
            size = size,
            value = value,
            modifier = Modifier.padding(top = SpacingScale.spacing03)
        )

        helperText?.let {
            Text(
                text = it,
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

/**
 * # Progress Bar - Inlined
 *
 * A progress bar provides feedback about the duration and progression of a process, such as a
 * download, file transfer, or installation, to indicate how long a user will be waiting.
 *
 * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
 *
 * @param value The progress value, between 0 and 1.
 * @param labelText The text label on top of the progress bar.
 * @param modifier The modifier to apply to this composable.
 * @param size The size type of the progress bar.
 */
@Composable
public fun InlinedProgressBar(
    @FloatRange(from = 0.0, to = 1.0) value: Float,
    labelText: String,
    modifier: Modifier = Modifier,
//    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    size: ProgressBarSize = ProgressBarSize.Big
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LabelText(text = labelText)
        ProgressBarTrack(
            size = size,
            value = value,
            modifier = Modifier
                .weight(1f)
                .padding(start = SpacingScale.spacing05)
        )
    }
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
        drawRect(
            color = theme.borderInteractive,
            size = this.size.copy(width = this.size.width * value)
        )
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
