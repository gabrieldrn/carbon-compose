package carbon.compose.progressbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.toggle.Toggle

private val progressBarStateOptions = ProgressBarState
    .entries
    .associateWith { DropdownOption(it.name) }

private val progressBarVariantOptions = mapOf(
    false to DropdownOption("Determinate"),
    true to DropdownOption("Indeterminate")
)

private val progressBarSizeOptions = ProgressBarSize
    .entries
    .associateWith { DropdownOption(it.name) }

@Composable
fun ProgressBarDemoScreen(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf<ProgressBarState>(ProgressBarState.Active) }
    var isIndeterminate by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf<ProgressBarSize>(ProgressBarSize.Small) }
    var isInlined by remember { mutableStateOf(false) }
    var isIndented by remember { mutableStateOf(false) }
    val isIndentedToggleEnabled by remember { derivedStateOf { !isInlined } }
    var progressBarVariantDropdownExpanded by remember { mutableStateOf(false) }
    var progressBarStateDropdownExpanded by remember { mutableStateOf(false) }
    val progressBarStateState by remember {
        derivedStateOf {
            if (isIndeterminate) DropdownInteractiveState.Disabled
            else DropdownInteractiveState.Enabled
        }
    }
    var progressBarSizeDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(SpacingScale.spacing05)
            .navigationBarsPadding()
    ) {
        Box(modifier = Modifier.height(100.dp)) {
            if (isIndeterminate) {
                IndeterminateProgressBar(
                    labelText = "Label text",
                    helperText = "Helper text",
                    size = size,
                    indented = isIndented,
                    inlined = isInlined,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                ProgressBar(
                    value = .75f,
                    labelText = "Label text",
                    helperText = "Helper text",
                    size = size,
                    indented = isIndented,
                    inlined = isInlined,
                    state = state,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(top = SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
            ) {
                Dropdown(
                    expanded = progressBarVariantDropdownExpanded,
                    placeholder = "Progress bar variant",
                    label = "Variant",
                    options = progressBarVariantOptions,
                    selectedOption = isIndeterminate,
                    onOptionSelected = { option -> isIndeterminate = option },
                    onExpandedChange = { progressBarVariantDropdownExpanded = it },
                    onDismissRequest = { progressBarVariantDropdownExpanded = false }
                )

                Dropdown(
                    expanded = progressBarStateDropdownExpanded,
                    placeholder = "Progress bar state",
                    label = "State",
                    options = progressBarStateOptions,
                    selectedOption = state,
                    state = progressBarStateState,
                    onOptionSelected = { option -> state = option },
                    onExpandedChange = { progressBarStateDropdownExpanded = it },
                    onDismissRequest = { progressBarStateDropdownExpanded = false }
                )

                Dropdown(
                    expanded = progressBarSizeDropdownExpanded,
                    placeholder = "Progress bar size",
                    label = "Size",
                    options = progressBarSizeOptions,
                    selectedOption = size,
                    onOptionSelected = { option -> size = option },
                    onExpandedChange = { progressBarSizeDropdownExpanded = it },
                    onDismissRequest = { progressBarSizeDropdownExpanded = false }
                )

                Toggle(
                    actionText = "Inlined",
                    isToggled = isInlined,
                    onToggleChange = { isInlined = it }
                )

                Toggle(
                    actionText = "Indented",
                    isToggled = isIndented,
                    isEnabled = isIndentedToggleEnabled,
                    onToggleChange = { isIndented = it }
                )
            }
        }
    }
}
