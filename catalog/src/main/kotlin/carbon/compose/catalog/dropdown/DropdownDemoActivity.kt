package carbon.compose.catalog.dropdown

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.compose.catalog.R
import carbon.compose.catalog.theme.CarbonCatalogTheme
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.DropdownOption
import carbon.compose.foundation.color.LocalCarbonTheme
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
                    modifier = Modifier.background(LocalCarbonTheme.current.background),
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
                    ) {
                        var selectedOption by remember { mutableStateOf<Int?>(null) }
                        var expanded by remember { mutableStateOf(false) }

                        Dropdown(
                            expanded = expanded,
                            fieldPlaceholderText = "Dropdown",
                            selectedOption = selectedOption,
                            options = dropdownOptions,
                            onOptionSelected = { selectedOption = it },
                            onExpandedChange = { expanded = it },
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            }
        }
    }
}
