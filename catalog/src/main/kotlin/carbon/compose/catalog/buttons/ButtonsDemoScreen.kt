package carbon.compose.catalog.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import carbon.android.button.Button
import carbon.android.button.ButtonSize
import carbon.android.button.CarbonButton
import carbon.android.button.IconButton
import carbon.android.foundation.spacing.SpacingScale
import carbon.android.toggle.Toggle
import carbon.compose.catalog.R

private val buttons = mapOf(
    "Primary" to CarbonButton.Primary,
    "Secondary" to CarbonButton.Secondary,
    "Tertiary" to CarbonButton.Tertiary,
    "Ghost" to CarbonButton.Ghost,
    "PrimaryDanger" to CarbonButton.PrimaryDanger,
    "TertiaryDanger" to CarbonButton.TertiaryDanger,
    "GhostDanger" to CarbonButton.GhostDanger,
)

@Composable
fun ButtonDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var isEnabled by rememberSaveable { mutableStateOf(true) }

        Toggle(
            label = "Enable buttons",
            isToggled = isEnabled,
            onToggleChange = { isEnabled = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpacingScale.spacing05)
                .padding(top = SpacingScale.spacing05)
        )

        Spacer(modifier = Modifier.height(SpacingScale.spacing05))

        // FIXME Expected performance issue when toggling isEnabled. Change this to
        //  present only one button, parameterized with toggle and dropdown
        //  components when available.
        ButtonsGrid(isEnabled)
    }
}

@Composable
private fun ButtonsGrid(
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    buttons.forEach { (label, buttonType) ->
        Row(
            modifier = modifier
                .padding(horizontal = SpacingScale.spacing05)
                .padding(bottom = SpacingScale.spacing05),
        ) {
            Button(
                label = label,
                onClick = {},
                buttonType = buttonType,
                buttonSize = ButtonSize.LargeProductive,
                isEnabled = isEnabled,
                iconPainter = painterResource(id = R.drawable.ic_add),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(SpacingScale.spacing05))
            IconButton(
                onClick = {},
                buttonType = buttonType,
                isEnabled = isEnabled,
                iconPainter = painterResource(id = R.drawable.ic_add),
            )
        }
    }
}
