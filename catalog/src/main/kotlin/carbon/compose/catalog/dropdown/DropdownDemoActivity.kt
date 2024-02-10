package carbon.compose.catalog.dropdown

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.uishell.UiShellHeader

class DropdownDemoActivity : AppCompatActivity() {

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
                        headerName = "Checkbox",
                        menuIconRes = R.drawable.ic_arrow_left,
                        onMenuIconPressed = { onBackPressedDispatcher.onBackPressed() },
                    )

                    Column(
                        modifier = Modifier
                            .verticalScroll(state = rememberScrollState())
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        var expanded by remember { mutableStateOf(false) }

                        Spacer(modifier = Modifier.height(300.dp))
                        Dropdown(
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            onDismissRequest = { expanded = false },
                            fieldPlaceholderText = "Dropdown",
                            optionSelected = null,
                            options = (0..5).associateWith { "Option $it" },
                            onOptionSelected = {},
                            modifier = Modifier.padding(8.dp),
                        )
                        Spacer(modifier = Modifier.height(900.dp))
                    }
                }
            }
        }
    }
}
