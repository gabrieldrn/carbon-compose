package carbon.compose.progressbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text
import carbon.compose.icons.CheckmarkFilledIcon
import carbon.compose.icons.ErrorFilledIcon

@Composable
internal fun ProgressBarRootLayout(
    labelText: String?,
    helperText: String?,
    inlined: Boolean,
    indented: Boolean,
    modifier: Modifier = Modifier,
    state: ProgressBarState = ProgressBarState.Active,
    colors: ProgressBarColors = ProgressBarColors.colors(),
    trackContent: @Composable () -> Unit
) {
    if (inlined) {
        InlinedProgressBarLayout(
            labelText = labelText,
            modifier = modifier,
            trackContent = trackContent
        )
    } else {
        DefaultProgressBarLayout(
            labelText = labelText,
            helperText = helperText,
            indented = indented,
            state = state,
            colors = colors,
            modifier = modifier,
            trackContent = trackContent
        )
    }
}

@Composable
internal fun DefaultProgressBarLayout(
    labelText: String?,
    helperText: String?,
    indented: Boolean,
    state: ProgressBarState,
    colors: ProgressBarColors,
    modifier: Modifier = Modifier,
    trackContent: @Composable () -> Unit
) {
    val iconColor by colors.iconColor(state)

    Column(modifier = modifier) {
        Row(
            modifier = if (indented) {
                Modifier.padding(horizontal = SpacingScale.spacing05)
            } else {
                Modifier
            }
        ) {
            if (labelText != null) {
                LabelText(
                    text = labelText,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Box(modifier = Modifier.weight(1f))
            }

            Icon(state = state, tint = iconColor)
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
                    start = if (indented) {
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
private fun InlinedProgressBarLayout(
    labelText: String?,
    modifier: Modifier = Modifier,
    trackContent: @Composable () -> Unit
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
private fun Icon(
    state: ProgressBarState,
    tint: Color,
    modifier: Modifier = Modifier
) {
    when (state) {
        ProgressBarState.Active -> {}
        ProgressBarState.Success -> CheckmarkFilledIcon(
            tint = tint,
            size = 16.dp,
            modifier = modifier
        )
        ProgressBarState.Error -> ErrorFilledIcon(
            tint = tint,
            size = 16.dp,
            modifier = modifier
        )
    }
}
