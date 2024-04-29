package carbon.compose.catalog.radiobutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
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
import carbon.compose.foundation.selectable.SelectableInteractiveState
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.radiobutton.RadioButton

@Composable
fun RadioButtonDemoScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        var checkedButton by rememberSaveable {
            mutableStateOf<SelectableInteractiveState>(SelectableInteractiveState.Default)
        }

        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .verticalScroll(state = rememberScrollState())
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(
                SpacingScale.spacing05,
                Alignment.CenterVertically
            )
        ) {
            SelectableInteractiveState.entries.forEach { interactiveState ->
                RadioButton(
                    selected = checkedButton == interactiveState,
                    label = when (interactiveState) {
                        SelectableInteractiveState.Default -> "Default"
                        SelectableInteractiveState.Disabled -> "Disabled"
                        SelectableInteractiveState.ReadOnly -> "Read-only"
                        SelectableInteractiveState.Error -> "Error"
                        SelectableInteractiveState.Warning -> "Warning"
                    },
                    onClick = { checkedButton = interactiveState },
                    errorMessage = "Error message goes here",
                    warningMessage = "Warning message goes here",
                    modifier = Modifier.fillMaxWidth(),
                    interactiveState = interactiveState,
                )
            }
        }
    }
}
