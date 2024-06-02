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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.toggle.Toggle

private val progressBarVariantOptions = mapOf(
    true to DropdownOption("Indeterminate"),
    false to DropdownOption("Determinate")
)

private val progressBarSizeOptions = mapOf(
    ProgressBarSize.Big to DropdownOption("Big"),
    ProgressBarSize.Small to DropdownOption("Small")
)

@Composable
fun ProgressBarDemoScreen(modifier: Modifier = Modifier) {
    var isIndeterminate by remember { mutableStateOf(false) }
    var progressBarSize by remember { mutableStateOf<ProgressBarSize>(ProgressBarSize.Small) }
    var isInlined by remember { mutableStateOf(false) }
    var isIndented by remember { mutableStateOf(false) }
    var progressBarVariantDropdownExpanded by remember { mutableStateOf(false) }
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
                    size = progressBarSize,
                    indented = isIndented,
                    inlined = isInlined,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                ProgressBar(
                    value = .75f,
                    labelText = "Label text",
                    helperText = "Helper text",
                    size = progressBarSize,
                    indented = isIndented,
                    inlined = isInlined,
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
                    expanded = progressBarSizeDropdownExpanded,
                    placeholder = "Progress bar size",
                    label = "Size",
                    options = progressBarSizeOptions,
                    selectedOption = progressBarSize,
                    onOptionSelected = { option -> progressBarSize = option },
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
                    onToggleChange = { isIndented = it }
                )
            }
        }
    }
}
