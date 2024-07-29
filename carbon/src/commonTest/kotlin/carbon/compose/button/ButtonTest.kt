package carbon.compose.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import carbon.compose.R
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.toList
import kotlin.test.Test

class ButtonTest {

    @Test
    fun button_base_validateLayout() = runComposeUiTest {
        setContent {
            Column(Modifier.fillMaxWidth()) {
                Button(
                    label = "Label",
                    onClick = {},
                    modifier = Modifier.testTag("Button 1")
                )
                Button(
                    label = "Label",
                    onClick = {},
                    modifier = Modifier
                        .width(300.dp)
                        .testTag("Button 2"),
                )
                Button(
                    label = "Label",
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Button 3"),
                )
            }
        }

        val nodes = listOf(
            onNodeWithTag("Button 1"),
            onNodeWithTag("Button 2"),
            onNodeWithTag("Button 3"),
        )

        val widths = onAllNodesWithTag("carbon_button_label", useUnmergedTree = true)
            .toList()
            .map {
                SpacingScale.spacing05 + // Start padding
                    it.getUnclippedBoundsInRoot().width + // Label width (varies by weight)
                    SpacingScale.spacing05 // End padding / Icon spacer
            }

        // Last button has fillMaxWidth
        onRoot().assertWidthIsEqualTo(widths.last())

        nodes.forEachIndexed { index, semanticsNodeInteraction ->
            semanticsNodeInteraction
                .assertExists()
                .assert(hasText("Label"))
                .assertHeightIsEqualTo(ButtonSize.LargeProductive.height)
                .assertWidthIsEqualTo(widths[index])
        }

        // Icon should not be present
        onAllNodesWithTag("carbon_button_icon", useUnmergedTree = true)
            .assertCountEquals(0)
    }

    @Test
    fun button_withIcon_validateLayout() = runComposeUiTest {
        setContent {
            Column(Modifier.fillMaxWidth()) {
                Button(
                    label = "Basic Icon",
                    onClick = {},
                    iconPainter = painterResource(id = R.drawable.ic_add),
                    modifier = Modifier.testTag("Button 1")
                )
                Button(
                    label = "Basic Icon",
                    onClick = {},
                    iconPainter = painterResource(id = R.drawable.ic_add),
                    modifier = Modifier
                        .width(300.dp)
                        .testTag("Button 2"),
                )
                Button(
                    label = "Basic Icon",
                    onClick = {},
                    iconPainter = painterResource(id = R.drawable.ic_add),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Button 3"),
                )
            }
        }

        val rootNodes = listOf(
            onNodeWithTag("Button 1"),
            onNodeWithTag("Button 2"),
            onNodeWithTag("Button 3"),
        )
        val buttonIconNodes = onAllNodesWithTag("carbon_button_icon", useUnmergedTree = true)
            .toList()

        val widths = onAllNodesWithTag("carbon_button_label", useUnmergedTree = true)
            .toList()
            .map {
                SpacingScale.spacing05 + // Start padding
                    it.getUnclippedBoundsInRoot().width + // Label width (varies by weight)
                    SpacingScale.spacing05 + // Icon spacer
                    16.dp + // Icon width
                    SpacingScale.spacing05 // End padding
            }

        assert(buttonIconNodes.size == 3)

        buttonIconNodes.forEach { it.assertIsDisplayed() }

        // Last button has fillMaxWidth
        onRoot().assertWidthIsEqualTo(widths.last())

        rootNodes.forEachIndexed { index, semanticsNodeInteraction ->
            semanticsNodeInteraction
                .assert(hasText("Basic Icon"))
                .assertHeightIsEqualTo(ButtonSize.LargeProductive.height)
                .assertWidthIsEqualTo(widths[index])
        }
    }

    @Test
    fun button_validateSemantics() = runComposeUiTest {
        var isEnabled by mutableStateOf(true)

        setContent {
            Button(
                label = "Label",
                onClick = {},
                isEnabled = isEnabled,
            )
        }

        onNodeWithText("Label").run {
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
