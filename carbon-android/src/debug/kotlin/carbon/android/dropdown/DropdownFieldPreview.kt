package carbon.android.dropdown

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
import carbon.android.CarbonDesignSystem
import carbon.android.dropdown.base.DropdownField
import carbon.android.dropdown.base.DropdownInteractiveState
import carbon.android.dropdown.base.DropdownPlaceholderText
import carbon.android.dropdown.base.DropdownSize
import carbon.android.dropdown.base.DropdownStateIcon
import carbon.android.foundation.spacing.SpacingScale

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
                )

                DropdownStateIcon(state = state)
            },
            modifier = Modifier.padding(SpacingScale.spacing03)
        )
    }
}
