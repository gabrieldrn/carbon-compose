package carbon.compose.catalog.buttons

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import carbon.compose.button.Button
import carbon.compose.button.ButtonSize
import carbon.compose.button.CarbonButton
import carbon.compose.button.IconButton
import carbon.compose.catalog.R
import carbon.compose.catalog.theme.CarbonCatalogTheme
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.toggle.Toggle
import carbon.compose.uishell.UiShellHeader

@Suppress("UndocumentedPublicClass")
class ButtonsDemoActivity : AppCompatActivity() {

    private val buttons = mapOf(
        "Primary" to CarbonButton.Primary,
        "Secondary" to CarbonButton.Secondary,
        "Tertiary" to CarbonButton.Tertiary,
        "Ghost" to CarbonButton.Ghost,
        "PrimaryDanger" to CarbonButton.PrimaryDanger,
        "TertiaryDanger" to CarbonButton.TertiaryDanger,
        "GhostDanger" to CarbonButton.GhostDanger,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )

        setContent {
            CarbonCatalogTheme {
                Column(
                    modifier = Modifier.background(LocalCarbonTheme.current.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UiShellHeader(
                        headerName = "Buttons",
                        menuIconRes = R.drawable.ic_arrow_left,
                        onMenuIconPressed = { onBackPressedDispatcher.onBackPressed() },
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = rememberScrollState())
                    ) {

                        var isEnabled by rememberSaveable { mutableStateOf(true) }

                        Toggle(
                            label = "Enable buttons",
                            isToggled = isEnabled,
                            onToggleChange = { isEnabled = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = SpacingScale.spacing05)
                                .padding(top = SpacingScale.spacing05)
                        )

                        Spacer(modifier = Modifier.height(SpacingScale.spacing05))

                        // FIXME Expected performance issue when toggling isEnabled. Change this to
                        //  present only one button, parameterized with toggle and dropdown
                        //  components when available.
                        ButtonsGrid(isEnabled)
                    }
                }
            }
        }
    }

    @Composable
    private fun ButtonsGrid(
        isEnabled: Boolean,
        modifier: Modifier = Modifier
    ) {
        buttons.forEach { (label, buttonType) ->
            Row(
                modifier = modifier
                    .padding(horizontal = SpacingScale.spacing05)
                    .padding(bottom = SpacingScale.spacing05),
            ) {
                Button(
                    label = label,
                    onClick = {},
                    buttonType = buttonType,
                    buttonSize = ButtonSize.LargeProductive,
                    isEnabled = isEnabled,
                    iconPainter = painterResource(id = R.drawable.ic_add),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(SpacingScale.spacing05))
                IconButton(
                    onClick = {},
                    buttonType = buttonType,
                    isEnabled = isEnabled,
                    iconPainter = painterResource(id = R.drawable.ic_add),
                )
            }
        }
    }
}
