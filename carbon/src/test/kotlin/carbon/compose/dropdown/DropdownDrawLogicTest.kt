package carbon.compose.dropdown

import carbon.compose.dropdown.domain.getOptionsPopupHeightRatio
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
}
