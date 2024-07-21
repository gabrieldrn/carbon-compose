package carbon.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.WhiteTheme
import org.junit.Rule

abstract class BaseColorsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    open val theme = WhiteTheme

    protected fun forAllLayers(block: @Composable (layer: Layer) -> Unit) {
        composeTestRule.setContent {
            Layer.entries.forEach { layer ->
                CarbonDesignSystem(theme = theme, layer = layer) {
                    block(layer)
                }
            }
        }
    }

    protected fun <T : Any> forAllLayersAndStates(
        statesUnderTest: Collection<T>,
        block: @Composable (state: T, layer: Layer) -> Unit
    ) {
        composeTestRule.setContent {
            statesUnderTest.forEach { state ->
                Layer.entries.forEach { layer ->
                    CarbonDesignSystem(theme = theme, layer = layer) {
                        block(state, layer)
                    }
                }
            }
        }
    }

    protected fun <T : Any, U : Any> forAllLayersAndStates(
        statesUnderTest1: Collection<T>,
        statesUnderTest2: Collection<U>,
        block: @Composable (state1: T, state2: U, layer: Layer) -> Unit
    ) {
        composeTestRule.setContent {
            statesUnderTest1.forEach { state1 ->
                statesUnderTest2.forEach { state2 ->
                    Layer.entries.forEach { layer ->
                        CarbonDesignSystem(theme = theme, layer = layer) {
                            block(state1, state2, layer)
                        }
                    }
                }
            }
        }
    }
}
