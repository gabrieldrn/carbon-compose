package carbon.compose.button

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import carbon.compose.R
import org.junit.Rule
import org.junit.Test

class IconButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun iconButton_validateLayout() {
        composeTestRule.setContent {
            IconButton(
                iconPainter = painterResource(id = R.drawable.ic_add),
                onClick = {},
                modifier = Modifier.testTag("IconButton")
            )
        }

        composeTestRule
            .onNodeWithTag("IconButton")
            .assertExists()
            .assertWidthIsEqualTo(48.dp)
            .assertHeightIsEqualTo(48.dp)
    }

    @Test
    fun iconButton_validateSemantics() {
        var isEnabled by mutableStateOf(true)
        composeTestRule.setContent {
            IconButton(
                iconPainter = painterResource(id = R.drawable.ic_add),
                onClick = { isEnabled = !isEnabled },
                isEnabled = isEnabled,
                modifier = Modifier.testTag("IconButton")
            )
        }

        composeTestRule.onNodeWithTag("IconButton").run {
            assert(
                hasClickAction() and
                    SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button) and
                    isEnabled() and
                    isFocusable()
            )

            isEnabled = false
            assertIsNotEnabled()
        }
    }
}
