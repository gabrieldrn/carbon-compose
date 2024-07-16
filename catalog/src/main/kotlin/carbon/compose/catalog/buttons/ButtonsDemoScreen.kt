package carbon.compose.catalog.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.button.Button
import carbon.compose.button.ButtonSize
import carbon.compose.button.ButtonType
import carbon.compose.button.IconButton
import carbon.compose.catalog.R
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.toggle.Toggle

private enum class ButtonVariant { Default, Icon }

private val buttonVariants = ButtonVariant.entries.associateWith { DropdownOption(it.name) }
private val buttonTypes = ButtonType.entries.associateWith { DropdownOption(it.name) }
private val buttonSizes = ButtonSize.entries.associateWith { DropdownOption(it.name) }

@Composable
@Suppress("DEPRECATION")
fun ButtonDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var buttonVariant by rememberSaveable { mutableStateOf(buttonVariants.keys.first()) }
        var buttonType by rememberSaveable { mutableStateOf(ButtonType.Primary) }
        var buttonSize by rememberSaveable { mutableStateOf(ButtonSize.LargeProductive) }
        var isEnabled by rememberSaveable { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(SpacingScale.spacing05),
            contentAlignment = Alignment.Center
        ) {
            val icon = painterResource(id = R.drawable.ic_add)

            when (buttonVariant) {
                ButtonVariant.Default -> Button(
                    label = buttonType.name,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    iconPainter = icon,
                    isEnabled = isEnabled,
                    buttonType = buttonType,
                    buttonSize = buttonSize
                )
                ButtonVariant.Icon -> IconButton(
                    onClick = {},
                    iconPainter = icon,
                    isEnabled = isEnabled,
                    buttonType = buttonType,
                )
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing04)
            ) {
                var buttonVariantDropdownExpanded by rememberSaveable { mutableStateOf(false) }
                var buttonTypeDropdownExpanded by rememberSaveable { mutableStateOf(false) }
                var buttonSizeDropdownExpanded by rememberSaveable { mutableStateOf(false) }

                BasicText(
                    text = "Configuration",
                    style = CarbonTypography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                Dropdown(
                    label = "Button variant",
                    expanded = buttonVariantDropdownExpanded,
                    placeholder = "Choose option",
                    options = buttonVariants,
                    selectedOption = buttonVariant,
                    onOptionSelected = { buttonVariant = it },
                    onExpandedChange = { buttonVariantDropdownExpanded = it },
                    onDismissRequest = { buttonVariantDropdownExpanded = false }
                )

                Dropdown(
                    label = "Button type",
                    expanded = buttonTypeDropdownExpanded,
                    placeholder = "Choose option",
                    options = buttonTypes,
                    selectedOption = buttonType,
                    onOptionSelected = { buttonType = it },
                    onExpandedChange = { buttonTypeDropdownExpanded = it },
                    onDismissRequest = { buttonTypeDropdownExpanded = false }
                )

                Dropdown(
                    label = "Button size",
                    expanded = buttonSizeDropdownExpanded,
                    placeholder = "Choose option",
                    options = buttonSizes,
                    selectedOption = buttonSize,
                    state = when {
                        buttonVariant == ButtonVariant.Icon ->
                            DropdownInteractiveState.Disabled
                        buttonSize == ButtonSize.Small ||
                        buttonSize == ButtonSize.Medium ->
                            DropdownInteractiveState.Warning("Discouraged size usage")
                        else ->
                            DropdownInteractiveState.Enabled
                    },
                    onOptionSelected = { buttonSize = it },
                    onExpandedChange = { buttonSizeDropdownExpanded = it },
                    onDismissRequest = { buttonSizeDropdownExpanded = false }
                )

                Toggle(
                    label = "Enable button",
                    isToggled = isEnabled,
                    onToggleChange = { isEnabled = it },
                )
            }
        }
    }
}
