package carbon.compose.catalog.dropdown

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
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
import carbon.compose.catalog.R
import carbon.compose.catalog.theme.CarbonCatalogTheme
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.DropdownInteractiveState
import carbon.compose.dropdown.DropdownOption
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.uishell.UiShellHeader

class DropdownDemoActivity : AppCompatActivity() {

    private val dropdownOptions: Map<Int, DropdownOption> = (0..9)
        .associateWith { DropdownOption("Option $it") }
        .toMutableMap()
        .apply {
            set(
                1, DropdownOption(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                        "nisi ut aliquip ex ea commodo consequat."
                )
            )
            set(2, DropdownOption("Disabled", enabled = false))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContent {
            CarbonCatalogTheme {
                Column(
                    modifier = Modifier
                        .background(LocalCarbonTheme.current.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UiShellHeader(
                        headerName = "Dropdown",
                        menuIconRes = R.drawable.ic_arrow_left,
                        onMenuIconPressed = { onBackPressedDispatcher.onBackPressed() },
                    )

                    Column(
                        modifier = Modifier
                            .verticalScroll(state = rememberScrollState())
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(SpacingScale.spacing03)
                            .padding(WindowInsets.navigationBars.asPaddingValues())
                        ,
                        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
                    ) {
                        DemoDropdown(title = "Dropdown")

                        DemoDropdown(
                            title = "Warning dropdown",
                            state = DropdownInteractiveState.Warning("Warning message goes here"),
                        )

                        DemoDropdown(
                            title = "Error dropdown",
                            state = DropdownInteractiveState.Error("Error message goes here"),
                        )

                        DemoDropdown(
                            title = "Disabled dropdown",
                            state = DropdownInteractiveState.Disabled,
                        )

                        DemoDropdown(
                            title = "Read-only dropdown",
                            state = DropdownInteractiveState.ReadOnly,
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun DemoDropdown(
        title: String,
        modifier: Modifier = Modifier,
        state: DropdownInteractiveState = DropdownInteractiveState.Enabled,
    ) {
        var selectedOption by remember { mutableStateOf<Int?>(null) }
        var expanded by remember { mutableStateOf(false) }

        Dropdown(
            label = "Dropdown",
            expanded = expanded,
            fieldPlaceholderText = title,
            selectedOption = selectedOption,
            options = dropdownOptions,
            onOptionSelected = { selectedOption = it },
            onExpandedChange = { expanded = it },
            onDismissRequest = { expanded = false },
            state = state,
            modifier = modifier,
        )
    }
}
