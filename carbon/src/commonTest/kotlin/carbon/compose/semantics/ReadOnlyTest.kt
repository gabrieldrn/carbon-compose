package carbon.compose.semantics

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

class ReadOnlyTest {

    @Test
    fun readOnly_validateSemantics() = runComposeUiTest {
        setContent {
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .readOnly(
                        role = Role.Checkbox,
                        state = ToggleableState.Indeterminate,
                        interactionSource = interactionSource,
                        mergeDescendants = true
                    )
                    .testTag("ReadOnly")
            )
        }

        onNodeWithTag("ReadOnly")
            .assertIsReadOnly {
                "The node misses the expected semantics for a read-only component."
            }
    }
}
