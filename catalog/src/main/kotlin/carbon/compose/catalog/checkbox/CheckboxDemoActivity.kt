package carbon.compose.catalog.checkbox

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import carbon.compose.catalog.R
import carbon.compose.catalog.theme.CarbonCatalogTheme
import carbon.compose.checkbox.Checkbox
import carbon.compose.checkbox.CheckboxInteractiveState
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.uishell.UiShellHeader

@Suppress("UndocumentedPublicClass")
class CheckboxDemoActivity : AppCompatActivity() {

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

                    var checkboxState by rememberSaveable {
                        mutableStateOf(ToggleableState.Off)
                    }

                    fun nextState() {
                        checkboxState = ToggleableState.entries.toTypedArray().let { states ->
                            states[(states.indexOf(checkboxState) + 1) % states.size]
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .width(IntrinsicSize.Max)
                            .verticalScroll(state = rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(
                            SpacingScale.spacing05,
                            Alignment.CenterVertically
                        )
                    ) {
                        CheckboxInteractiveState.entries.forEach { interactiveState ->
                            Checkbox(
                                state = checkboxState,
                                interactiveState = interactiveState,
                                label = when (interactiveState) {
                                    CheckboxInteractiveState.Default -> "Default"
                                    CheckboxInteractiveState.Disabled -> "Disabled"
                                    CheckboxInteractiveState.ReadOnly -> "Read-only"
                                    CheckboxInteractiveState.Error -> "Error"
                                    CheckboxInteractiveState.Warning -> "Warning"
                                },
                                onClick = ::nextState,
                                errorMessage = "Error message goes here",
                                warningMessage = "Warning message goes here",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
