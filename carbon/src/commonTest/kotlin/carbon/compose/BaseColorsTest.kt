package carbon.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ComposeUiTest
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.WhiteTheme

abstract class BaseColorsTest {

    open val theme = WhiteTheme

    protected fun ComposeUiTest.forAllLayers(block: @Composable (layer: Layer) -> Unit) {
        setContent {
            Layer.entries.forEach { layer ->
                CarbonDesignSystem(theme = theme, layer = layer) {
                    block(layer)
                }
            }
        }
    }

    protected fun <T : Any> ComposeUiTest.forAllLayersAndStates(
        statesUnderTest: Collection<T>,
        block: @Composable (state: T, layer: Layer) -> Unit
    ) {
        setContent {
            statesUnderTest.forEach { state ->
                Layer.entries.forEach { layer ->
                    CarbonDesignSystem(theme = theme, layer = layer) {
                        block(state, layer)
                    }
                }
            }
        }
    }

    protected fun <T : Any, U : Any> ComposeUiTest.forAllLayersAndStates(
        statesUnderTest1: Collection<T>,
        statesUnderTest2: Collection<U>,
        block: @Composable (state1: T, state2: U, layer: Layer) -> Unit
    ) {
        setContent {
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
