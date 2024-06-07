package carbon.compose.catalog.toggle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import carbon.compose.Carbon
import carbon.compose.catalog.misc.LayerSelectionDropdown
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.toggle.SmallToggle
import carbon.compose.toggle.Toggle

@Composable
fun ToggleDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var isToggled by rememberSaveable { mutableStateOf(false) }
        var layer by remember { mutableStateOf(Layer.Layer00) }

        CarbonLayer(layer = layer) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .containerBackground()
                    .padding(SpacingScale.spacing05)
            ) {
                DefaultToggles(
                    isToggled = isToggled,
                    onToggleChange = { isToggled = it }
                )
//                SmallToggles(
//                    isToggled = isToggled,
//                    onToggleChange = { isToggled = it },
//                    modifier = Modifier.padding(top = SpacingScale.spacing05)
//                )
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05)
            ) {
                BasicText(
                    text = "Configuration",
                    style = CarbonTypography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                LayerSelectionDropdown(
                    selectedLayer = layer,
                    onLayerSelected = { layer = it },
                    modifier = Modifier.padding(top = SpacingScale.spacing03)
                )
            }
        }
    }
}

@Composable
private fun DefaultToggles(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
    ) {
        Toggle(
            isToggled = isToggled,
            onToggleChange = onToggleChange,
            label = "Toggle",
//            actionText = if (isToggled) "On" else "Off",
        )
//        Toggle(
//            isToggled = isToggled,
//            isEnabled = false,
//            onToggleChange = {},
//            actionText = "Disabled",
//        )
//        Toggle(
//            isToggled = isToggled,
//            isReadOnly = true,
//            onToggleChange = {},
//            actionText = "Read only",
//        )
    }
}

@Composable
private fun SmallToggles(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
    ) {
        SmallToggle(
            isToggled = isToggled,
            onToggleChange = onToggleChange,
            actionText = if (isToggled) "On" else "Off",
        )
        SmallToggle(
            isToggled = isToggled,
            isEnabled = false,
            onToggleChange = {},
            actionText = "Disabled",
        )
        SmallToggle(
            isToggled = isToggled,
            isReadOnly = true,
            onToggleChange = {},
            actionText = "Read only",
        )
    }
}
