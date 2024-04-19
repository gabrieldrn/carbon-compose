package carbon.compose.catalog.toggle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.toggle.SmallToggle
import carbon.compose.toggle.Toggle

@Composable
fun ToggleDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var isToggled by rememberSaveable { mutableStateOf(false) }

        DefaultToggles(
            isToggled = isToggled,
            onToggleChange = { isToggled = it }
        )
        SmallToggles(
            isToggled = isToggled,
            onToggleChange = { isToggled = it },
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun DefaultToggles(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Toggle(
            isToggled = isToggled,
            onToggleChange = onToggleChange,
            label = "Toggle",
            actionText = if (isToggled) "On" else "Off",
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
        Toggle(
            isToggled = isToggled,
            isEnabled = false,
            onToggleChange = {},
            actionText = "Disabled",
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
        Toggle(
            isToggled = isToggled,
            isReadOnly = true,
            onToggleChange = {},
            actionText = "Read only",
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
    }
}

@Composable
private fun SmallToggles(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SmallToggle(
            isToggled = isToggled,
            onToggleChange = onToggleChange,
            actionText = if (isToggled) "On" else "Off",
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
        SmallToggle(
            isToggled = isToggled,
            isEnabled = false,
            onToggleChange = {},
            actionText = "Disabled",
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
        SmallToggle(
            isToggled = isToggled,
            isReadOnly = true,
            onToggleChange = {},
            actionText = "Read only",
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
    }
}
