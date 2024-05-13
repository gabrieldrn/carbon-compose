package carbon.compose.catalog.radiobutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
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
import carbon.android.foundation.selectable.SelectableInteractiveState
import carbon.android.foundation.spacing.SpacingScale
import carbon.android.radiobutton.RadioButton

private val interactiveStates = listOf(
    SelectableInteractiveState.Default,
    SelectableInteractiveState.Disabled,
    SelectableInteractiveState.ReadOnly,
    SelectableInteractiveState.Error("Error message goes here"),
    SelectableInteractiveState.Warning("Warning message goes here")
)

@Composable
fun RadioButtonDemoScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        var checkedButton by rememberSaveable {
            mutableStateOf(SelectableInteractiveState.Default.toString())
        }

        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .fillMaxHeight()
                .verticalScroll(state = rememberScrollState())
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(
                SpacingScale.spacing05,
                Alignment.CenterVertically
            )
        ) {
            interactiveStates.forEach { interactiveState ->
                RadioButton(
                    selected = checkedButton == interactiveState.toString(),
                    label = when (interactiveState) {
                        is SelectableInteractiveState.Default -> "Default"
                        is SelectableInteractiveState.Disabled -> "Disabled"
                        is SelectableInteractiveState.ReadOnly -> "Read-only"
                        is SelectableInteractiveState.Error -> "Error"
                        is SelectableInteractiveState.Warning -> "Warning"
                    },
                    onClick = { checkedButton = interactiveState.toString() },
                    modifier = Modifier.fillMaxWidth(),
                    interactiveState = interactiveState,
                )
            }
        }
    }
}
