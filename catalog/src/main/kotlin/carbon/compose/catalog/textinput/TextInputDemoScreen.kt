package carbon.compose.catalog.textinput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.button.Button
import carbon.compose.button.IconButton
import carbon.compose.catalog.R
import carbon.compose.catalog.misc.LayerSelectionDropdown
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.dropdownOptionsOf
import carbon.compose.dropdown.base.toDropdownOptions
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.textinput.TextArea
import carbon.compose.textinput.TextInput
import carbon.compose.textinput.TextInputState

private const val TEXT_INPUT_VARIANT = "Text input (single line)"
private const val TEXT_AREA_VARIANT = "Text area"
private val textInputStateOptions = TextInputState.entries.toDropdownOptions()
private val textInputVariantOptions = dropdownOptionsOf(TEXT_INPUT_VARIANT, TEXT_AREA_VARIANT)

private val loremIpsum =
    """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut 
        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco 
        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in 
        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat 
        cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum
    """.trimIndent().replace("\n","")

@Composable
fun TextInputDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }
        var textInputState by rememberSaveable { mutableStateOf(TextInputState.Enabled) }
        var variant by rememberSaveable { mutableStateOf(TEXT_INPUT_VARIANT) }
        var text by rememberSaveable { mutableStateOf("") }

        CarbonLayer(layer = layer) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .containerBackground()
                    .padding(SpacingScale.spacing05)
            ) {
                if (variant == TEXT_INPUT_VARIANT) {
                    TextInput(
                        label = "Label",
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.align(Alignment.Center),
                        placeholderText = "Placeholder",
                        helperText = textInputState.name,
                        state = textInputState,
                    )
                } else {
                    TextArea(
                        label = "Label",
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.align(Alignment.Center),
                        placeholderText = "Placeholder",
                        helperText = textInputState.name,
                        state = textInputState,
                        maxLines = 5
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
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
            ) {
                var textFieldStateDropdownExpanded by remember { mutableStateOf(false) }
                var variantDropdownExpanded by remember { mutableStateOf(false) }

                BasicText(
                    text = "Configuration",
                    style = CarbonTypography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                Dropdown(
                    label = "Text input state",
                    expanded = textFieldStateDropdownExpanded,
                    placeholder = "Choose an option",
                    options = textInputStateOptions,
                    selectedOption = textInputState,
                    onOptionSelected = { textInputState = it },
                    onExpandedChange = { textFieldStateDropdownExpanded = it },
                    onDismissRequest = { textFieldStateDropdownExpanded = false }
                )

                Dropdown(
                    label = "Text input variant",
                    expanded = variantDropdownExpanded,
                    placeholder = "Choose a variant",
                    options = textInputVariantOptions,
                    selectedOption = variant,
                    onOptionSelected = { variant = it },
                    onExpandedChange = { variantDropdownExpanded = it },
                    onDismissRequest = { variantDropdownExpanded = false }
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        label = "Lorem ipsum",
                        onClick = { text = loremIpsum },
                        iconPainter = painterResource(id = R.drawable.ic_text_long_paragraph),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        iconPainter = painterResource(id = R.drawable.ic_delete),
                        onClick = { text = "" },
                        modifier = Modifier.padding(start = SpacingScale.spacing03)
                    )
                }

                LayerSelectionDropdown(
                    selectedLayer = layer,
                    onLayerSelected = { layer = it },
                )
            }
        }
    }
}
