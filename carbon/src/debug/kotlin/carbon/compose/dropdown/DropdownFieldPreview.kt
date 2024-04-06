package carbon.compose.dropdown

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import carbon.compose.CarbonDesignSystem
import carbon.compose.dropdown.base.DropdownField
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownPlaceholderText
import carbon.compose.dropdown.base.DropdownSize
import carbon.compose.dropdown.base.DropdownStateIcon
import carbon.compose.foundation.spacing.SpacingScale

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DropdownFieldPreview() {
    var state by remember {
        mutableStateOf<DropdownInteractiveState>(DropdownInteractiveState.Enabled)
    }
    val expandedStates = remember { MutableTransitionState(false) }
    val transition = updateTransition(expandedStates, "Dropdown")

    CarbonDesignSystem {
        DropdownField(
            state = state,
            dropdownSize = DropdownSize.Large,
            transition = transition,
            expandedStates = expandedStates,
            onExpandedChange = { expandedStates.targetState = it },
            fieldContent = {
                DropdownPlaceholderText(
                    placeholderText = "Placeholder",
                    state = state,
                    modifier = Modifier.weight(1f)
                )

                DropdownStateIcon(state = state)
            },
            modifier = Modifier.padding(SpacingScale.spacing03)
        )
    }
}
