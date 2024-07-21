package carbon.compose.catalog.toggle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.catalog.misc.LayerSelectionDropdown
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.dropdownOptionsOf
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.toggle.SmallToggle
import carbon.compose.toggle.Toggle

private const val SmallToggleType = "Small"
private const val DefaultToggleType = "Default"
private val toggleTypesOptions = dropdownOptionsOf(DefaultToggleType, SmallToggleType)

@Composable
fun ToggleDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var toggleType by rememberSaveable {
            mutableStateOf(DefaultToggleType)
        }
        var isEnabled by rememberSaveable { mutableStateOf(true) }
        var isReadOnly by rememberSaveable { mutableStateOf(false) }
        var isToggled by rememberSaveable { mutableStateOf(false) }
        var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }

        CarbonLayer(layer = layer) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .containerBackground()
                    .padding(SpacingScale.spacing05)
            ) {
                if (toggleType == DefaultToggleType) {
                    Toggle(
                        label = "Toggle",
                        isToggled = isToggled,
                        isEnabled = isEnabled,
                        isReadOnly = isReadOnly,
                        onToggleChange = { isToggled = it },
                        actionText = "Action text",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    SmallToggle(
                        isToggled = isToggled,
                        isEnabled = isEnabled,
                        isReadOnly = isReadOnly,
                        onToggleChange = { isToggled = it },
                        actionText = "Action text",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing03)
            ) {
                BasicText(
                    text = "Configuration",
                    style = CarbonTypography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                Dropdown(
                    label = "Toggle type",
                    placeholder = "Choose option",
                    options = toggleTypesOptions,
                    selectedOption = toggleType,
                    onOptionSelected = { toggleType = it },
                )

                Toggle(
                    label = "Enabled",
                    isToggled = isEnabled,
                    onToggleChange = { isEnabled = it },
                )

                Toggle(
                    label = "Read only",
                    isToggled = isReadOnly,
                    onToggleChange = { isReadOnly = it },
                )

                Spacer(
                    modifier = Modifier
                        .background(Carbon.theme.borderStrong01)
                        .fillMaxWidth()
                        .height(1.dp)
                )

                LayerSelectionDropdown(
                    selectedLayer = layer,
                    onLayerSelected = { layer = it },
                )
            }
        }
    }
}
