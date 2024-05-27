package carbon.compose.dropdown

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import carbon.compose.CarbonDesignSystem
import carbon.compose.dropdown.base.DropdownColors
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.WhiteTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class DropdownColorsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val theme = WhiteTheme
    private var layer by mutableStateOf<Layer>(Layer.Layer00)
    private var dropdownColors by mutableStateOf<DropdownColors?>(null)

    private val interactiveStates = mapOf(
        "enabled" to DropdownInteractiveState.Enabled,
        "warning" to DropdownInteractiveState.Warning("warning"),
        "error" to DropdownInteractiveState.Error("error"),
        "disabled" to DropdownInteractiveState.Disabled,
        "readOnly" to DropdownInteractiveState.ReadOnly
    )

    private fun Map<Any, Any>.getColorByStateAndLayer(
        state: Any,
        layer: Layer
    ) = (this[state] ?: this[layer]!!).let {
        if (it is Map<*, *>) it[layer] as Color
        else it
    }

    private fun checkColorByStateAndLayer(
        expectedColors: Map<Any, Any>,
        actual: (state: DropdownInteractiveState, layer: Layer) -> Color
    ) {
        interactiveStates.values.forEach { state ->
            Layer.entries.forEach { layer ->
                this.layer = layer
                composeTestRule.waitForIdle()

                assertEquals(
                    expected = expectedColors.getColorByStateAndLayer(state, layer),
                    actual = actual(state, layer),
                    message = "State: $state, Layer: $layer"
                )
            }
        }
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            CarbonDesignSystem(
                theme = theme,
                layer = layer
            ) {
                dropdownColors = DropdownColors.colors()
            }
        }
    }

    @Test
    fun dropdownColors_staticColorsWithoutContextualLayer_colorsAreCorrect() {
        assertEquals(
            expected = theme.iconPrimary,
            actual = dropdownColors!!.checkmarkIconColor
        )

        assertEquals(
            expected = theme.supportError,
            actual = dropdownColors!!.fieldBorderErrorColor
        )
    }

    @Test
    fun dropdownColors_menuOptionBackgroundColor_colorsAreCorrect() {
        Layer.values().forEach {
            layer = it
            composeTestRule.waitForIdle()
            assertEquals(
                expected = when (it) {
                    Layer.Layer00 -> theme.layer01
                    Layer.Layer01 -> theme.layer02
                    Layer.Layer02 -> theme.layer03
                    Layer.Layer03 -> theme.layer03
                },
                actual = dropdownColors!!.menuOptionBackgroundColor,
                message = "Layer: $it"
            )
        }
    }

    @Test
    fun dropdownColors_menuOptionBorderColor_colorsAreCorrect() {
        Layer.values().forEach {
            layer = it
            composeTestRule.waitForIdle()
            assertEquals(
                expected = when (it) {
                    Layer.Layer00 -> theme.borderSubtle01
                    Layer.Layer01 -> theme.borderSubtle02
                    Layer.Layer02 -> theme.borderSubtle03
                    Layer.Layer03 -> theme.borderSubtle03
                },
                actual = dropdownColors!!.menuOptionBorderColor,
                message = "Layer: $it"
            )
        }
    }

    @Test
    fun dropdownColors_chevronIconColor_colorsAreCorrect() {
        val expectedColors =
            (interactiveStates.values + Layer.entries).associate {
                if (it is DropdownInteractiveState.Disabled) it to theme.iconDisabled
                else it to theme.iconPrimary
            }

        checkColorByStateAndLayer(expectedColors) { state, _ ->
            dropdownColors!!.chevronIconColor(state)
        }
    }

    @Test
    fun dropdownColors_fieldBackgroundColor_colorsAreCorrect() {
        val colorsExpected = mapOf(
            DropdownInteractiveState.ReadOnly to Color.Transparent,
            Layer.Layer00 to theme.field01,
            Layer.Layer01 to theme.field02,
            Layer.Layer02 to theme.field03,
            Layer.Layer03 to theme.field03,
        )

        checkColorByStateAndLayer(colorsExpected) { state, _ ->
            dropdownColors!!.fieldBackgroundColor(state)
        }
    }

    @Test
    fun dropdownColors_fieldBorderColor_colorsAreCorrect() {
        val colorsExpected = mapOf(
            interactiveStates["error"]!! to theme.supportError,
            interactiveStates["disabled"]!! to Color.Transparent,
            interactiveStates["readOnly"]!! to mapOf(
                Layer.Layer00 to theme.borderSubtle01,
                Layer.Layer01 to theme.borderSubtle02,
                Layer.Layer02 to theme.borderSubtle03,
                Layer.Layer03 to theme.borderSubtle03
            ),
            Layer.Layer00 to theme.borderStrong01,
            Layer.Layer01 to theme.borderStrong02,
            Layer.Layer02 to theme.borderStrong03,
            Layer.Layer03 to theme.borderStrong03,
        )

        checkColorByStateAndLayer(colorsExpected) { state, _ ->
            dropdownColors!!.fieldBorderColor(state)
        }
    }

    @Test
    fun dropdownColors_fieldTextColor_colorsAreCorrect() {
        val colorsExpected = (interactiveStates.values + Layer.entries).associate {
            if (it is DropdownInteractiveState.Disabled) it to theme.textDisabled
            else it to theme.textPrimary
        }

        checkColorByStateAndLayer(colorsExpected) { state, _ ->
            dropdownColors!!.fieldTextColor(state)
        }
    }

    @Test
    fun dropdownColors_helperTextColor_colorsAreCorrect() {
        val colorsExpected = (interactiveStates.values + Layer.entries).associate {
            if (it is DropdownInteractiveState.Error) it to theme.textError
            else it to theme.textPrimary
        }

        checkColorByStateAndLayer(colorsExpected) { state, _ ->
            dropdownColors!!.helperTextColor(state)
        }
    }

    @Test
    fun dropdownColors_labelTextColor_colorsAreCorrect() {
        val colorsExpected = (interactiveStates.values + Layer.entries).associate {
            if (it is DropdownInteractiveState.Disabled) it to theme.textDisabled
            else it to theme.textSecondary
        }

        checkColorByStateAndLayer(colorsExpected) { state, _ ->
            dropdownColors!!.labelTextColor(state)
        }
    }

    @Test
    fun dropdownColors_menuOptionBackgroundSelectedColor_colorsAreCorrect() {
        listOf(true, false).forEach { isSelected ->
            Layer.entries.forEach { layer ->
                this.layer = layer
                composeTestRule.waitForIdle()

                assertEquals(
                    expected = if (isSelected) {
                        when (layer) {
                            Layer.Layer00 -> theme.layerSelected01
                            Layer.Layer01 -> theme.layerSelected02
                            Layer.Layer02 -> theme.layerSelected03
                            Layer.Layer03 -> theme.layerSelected03
                        }
                    } else {
                        Color.Transparent
                    },
                    actual = dropdownColors!!.menuOptionBackgroundSelectedColor(
                        isSelected = isSelected
                    ),
                    message = "isSelected: $isSelected, Layer: $layer"
                )
            }
        }
    }

    @Test
    fun dropdownColors_menuOptionTextColor_colorsAreCorrect() {
        listOf(true, false).forEach { isEnabled ->
            listOf(true, false).forEach { isSelected ->
                Layer.entries.forEach { layer ->
                    assertEquals(
                        expected = when {
                            !isEnabled -> theme.textDisabled
                            isSelected -> theme.textPrimary
                            else -> theme.textSecondary
                        },
                        actual = dropdownColors!!.menuOptionTextColor(
                            isEnabled = isEnabled,
                            isSelected = isSelected
                        ),
                        message = "isEnabled: $isEnabled, isSelected: $isSelected, Layer: $layer"
                    )
                }
            }
        }
    }
}
