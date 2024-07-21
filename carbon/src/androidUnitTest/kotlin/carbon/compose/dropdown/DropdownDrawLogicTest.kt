package carbon.compose.dropdown

import androidx.compose.ui.unit.dp
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.domain.getChevronStartSpacing
import carbon.compose.dropdown.domain.getOptionsPopupHeightRatio
import carbon.compose.foundation.spacing.SpacingScale
import org.junit.Test
import kotlin.test.assertEquals

class DropdownDrawLogicTest {

    @Test
    fun dropdownDrawLogic_getOptionsPopupHeightRatio_returnsCorrectValue() {
        mapOf(
            1 to 0 to 1f,
            1 to 1 to 1f,
            1 to 2 to 1f,
            2 to 0 to 1.5f,
            2 to 1 to 1.5f,
            2 to 2 to 2f,
            3 to 0 to 1.5f,
            3 to 1 to 1.5f,
            3 to 2 to 2.5f,
            3 to 3 to 3f,
            4 to 3 to 3.5f,
        ).forEach { (optionsSize, visibleItems), expectedRatio ->
            val actualRatio = getOptionsPopupHeightRatio(
                optionsSize = optionsSize,
                visibleItemsBeforeScroll = visibleItems
            )
            assertEquals(
                expected = expectedRatio,
                actual = actualRatio,
                message = "For optionsSize=$optionsSize " +
                    "and visibleItemsBeforeScroll=$visibleItems, " +
                    "expected $expectedRatio " +
                    "but was $actualRatio"
            )
        }
    }

    @Test
    fun dropdownDrawLogic_getChevronStartSpacing_returnsCorrectValue() {
        mapOf(
            DropdownInteractiveState.Enabled to SpacingScale.spacing05,
            DropdownInteractiveState.Warning("") to 0.dp,
            DropdownInteractiveState.Error("") to 0.dp,
            DropdownInteractiveState.Disabled to SpacingScale.spacing05,
            DropdownInteractiveState.ReadOnly to SpacingScale.spacing05,
        ).forEach { (state, expectedSpacing) ->
            val actualSpacing = getChevronStartSpacing(state)
            assertEquals(
                expected = expectedSpacing,
                actual = actualSpacing,
                message = "For state=$state, expected $expectedSpacing but was $actualSpacing"
            )
        }
    }
}
