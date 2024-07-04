package carbon.compose.foundation.selectable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text
import carbon.compose.icons.WarningIcon
import carbon.compose.icons.WarningAltIcon

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
        WarningIcon(
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
        WarningAltIcon(Modifier.padding(2.dp))
        Text(
            text = warningMessage,
            color = colors.warningMessageTextColor,
            modifier = Modifier.padding(start = SpacingScale.spacing03),
            style = CarbonTypography.label01
        )
    }
}
