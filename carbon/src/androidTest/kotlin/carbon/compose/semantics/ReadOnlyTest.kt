package carbon.compose.semantics

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.isNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class ReadOnlyTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun readOnly_validateSemantics() {
        composeTestRule.setContent {
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

        composeTestRule
            .onNodeWithTag("ReadOnly")
            .assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.ToggleableState,
                    ToggleableState.Indeterminate
                ) and
                    SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox) and
                    isNotEnabled() and
                    SemanticsMatcher.expectValue(CarbonSemanticsProperties.ReadOnly, true)
            ) {
                "The node misses the expected semantics for a read-only component."
            }
    }
}
