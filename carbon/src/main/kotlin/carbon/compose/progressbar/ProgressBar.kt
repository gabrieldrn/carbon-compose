package carbon.compose.progressbar

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import carbon.compose.Carbon
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text

///**
// * Alignments for [ProgressBar] text labels. Text alignment depends on the context and area given.
// *
// * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
// */
//public enum class ProgressBarTextAlignment {
//    Default, Inline, Indented
//}

/**
 * The [ProgressBar] is offered in two different sizes—big (8px) and small (4px). The big progress
 * bar height is typically used when there is large amounts of space on a page. The small progress
 * bar height is commonly used when space is restricted and can be placed within cards, data tables,
 * or side panels.
 *
 * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
 */
public enum class ProgressBarSize {
    Big, Small
}

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
 * @param size The size type of the progress bar.
 */
@Composable
public fun ProgressBar(
    @FloatRange(from = 0.0, to = 1.0) value: Float,
    modifier: Modifier = Modifier,
//    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    labelText: String? = null,
    helperText: String? = null,
//    labelAlignment: ProgressBarTextAlignment = ProgressBarTextAlignment.Default,
    size: ProgressBarSize = ProgressBarSize.Big
) {
    val theme = Carbon.theme

    Column(modifier = modifier) {
        labelText?.let {
            Text(
                text = it,
                style = CarbonTypography.label01,
                color = Carbon.theme.textPrimary
            )
        }

        Canvas(
            modifier = Modifier
                .padding(top = SpacingScale.spacing03)
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

        helperText?.let {
            Text(
                text = it,
                style = CarbonTypography.helperText01,
                color = Carbon.theme.textHelper,
                modifier = Modifier.padding(top = SpacingScale.spacing03)
            )
        }
    }
}
