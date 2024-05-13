package carbon.android.foundation.selectable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.android.foundation.spacing.SpacingScale
import carbon.android.foundation.text.CarbonTypography
import carbon.android.foundation.text.Text
import carbon.android.icons.ErrorIcon
import carbon.android.icons.WarningIcon

@Composable
internal fun ErrorContent(
    colors: SelectableColors,
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ErrorIcon(
            modifier = Modifier.padding(SpacingScale.spacing01)
        )
        Text(
            text = errorMessage,
            color = colors.errorMessageTextColor,
            modifier = Modifier.padding(start = SpacingScale.spacing03),
            style = CarbonTypography.label01
        )
    }
}

@Composable
internal fun WarningContent(
    colors: SelectableColors,
    warningMessage: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WarningIcon(Modifier.padding(2.dp))
        Text(
            text = warningMessage,
            color = colors.warningMessageTextColor,
            modifier = Modifier.padding(start = SpacingScale.spacing03),
            style = CarbonTypography.label01
        )
    }
}
