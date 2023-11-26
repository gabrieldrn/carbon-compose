package carbon.compose.checkbox

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import carbon.compose.checkbox.CheckboxInteractiveState.Companion.isEnabled
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text

private val checkboxBorderWidth = 1.dp
private val checkboxCornerRadius = 2.dp

@Composable
public fun Checkbox(
    state: ToggleableState,
    interactiveState: CheckboxInteractiveState,
    label: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val colors = CheckboxColors.colors()
    val checkmarkIcon = rememberVectorPainter(image = checkboxCheckmarkIcon)
    val indeterminateIcon = rememberVectorPainter(image = checkboxIndeterminateIcon)
    val icon = when (state) {
        ToggleableState.On -> checkmarkIcon
        ToggleableState.Indeterminate -> indeterminateIcon
        ToggleableState.Off -> null
    }

    val checkboxModifier = if (onClick != null) {
        Modifier.triStateToggleable(
            state = state,
            interactionSource = interactionSource,
            enabled = interactiveState.isEnabled,
            onClick = onClick,
            indication = null
        )
    } else {
        Modifier
    }

    Row(
        modifier = modifier
            .height(20.dp)
            .then(checkboxModifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Canvas(modifier = Modifier.requiredSize(16.dp)) {
            val borderWidth = checkboxBorderWidth.toPx()

            // Background
            drawRoundRect(
                color = colors.backgroundColor(
                    interactiveState = interactiveState,
                    state = state
                ),
                cornerRadius = CornerRadius(checkboxCornerRadius.toPx()),
            )
            // Border
            drawRoundRect(
                color = colors.borderColor(
                    interactiveState = interactiveState,
                    state = state
                ),
                topLeft = Offset(borderWidth, borderWidth) * 0.5f,
                size = Size(
                    width = size.width - borderWidth,
                    height = size.height - borderWidth
                ),
                cornerRadius = CornerRadius(checkboxCornerRadius.toPx()),
                style = Stroke(borderWidth)
            )
            // Checkmark
            icon?.run {
                draw(
                    size = intrinsicSize.div(2f),
                    colorFilter = ColorFilter.tint(
                        colors.checkmarkColor(
                            interactiveState = interactiveState,
                            state = state
                        )
                    )
                )
            }
        }
        Text(
            text = label,
            color = colors.labelColor(interactiveState = interactiveState),
            modifier = Modifier
                .padding(start = SpacingScale.spacing03)
                .height(20.dp),
            style = CarbonTypography.bodyCompact01
        )
    }
}

public enum class CheckboxInteractiveState {

    Default,
    Disabled,
    ReadOnly,
    Error,
    Warning;

    internal companion object {

        private val enabledStates = arrayOf(Default, Error, Warning)

        val CheckboxInteractiveState.isEnabled: Boolean
            get() = this in enabledStates
    }
}

@Immutable
internal class CheckboxColors(
    val borderColor: Color,
    val borderDisabledColor: Color,
    val borderReadOnlyColor: Color,
    val borderErrorColor: Color,

    val backgroundCheckedColor: Color,
    val backgroundDisabledCheckedColor: Color,

    val checkmarkCheckedColor: Color,
    val checkmarkReadOnlyCheckedColor: Color,

    val labelColor: Color,
    val labelDisabledColor: Color,

    val errorMessageTextColor: Color,
    val errorIconColor: Color,
    val errorIconInnerFillColor: Color,

    val warningMessageTextColor: Color,
    val warningIconColor: Color,
    val warningIconInnerFillColor: Color,
) {

    fun borderColor(
        interactiveState: CheckboxInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        CheckboxInteractiveState.Default,
        CheckboxInteractiveState.Warning ->
            if (state == ToggleableState.Off) borderColor else Color.Transparent

        CheckboxInteractiveState.Disabled ->
            if (state == ToggleableState.Off) borderDisabledColor else Color.Transparent

        CheckboxInteractiveState.ReadOnly -> borderReadOnlyColor
        CheckboxInteractiveState.Error -> borderErrorColor
    }

    fun backgroundColor(
        interactiveState: CheckboxInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        CheckboxInteractiveState.Default,
        CheckboxInteractiveState.Error,
        CheckboxInteractiveState.Warning ->
            if (state == ToggleableState.Off) Color.Transparent else backgroundCheckedColor

        CheckboxInteractiveState.Disabled ->
            if (state == ToggleableState.Off) Color.Transparent else backgroundDisabledCheckedColor

        CheckboxInteractiveState.ReadOnly -> Color.Transparent
    }

    fun checkmarkColor(
        interactiveState: CheckboxInteractiveState,
        state: ToggleableState
    ): Color = when {
        state == ToggleableState.Off -> Color.Transparent
        interactiveState == CheckboxInteractiveState.ReadOnly -> checkmarkReadOnlyCheckedColor
        else -> checkmarkCheckedColor
    }

    fun labelColor(interactiveState: CheckboxInteractiveState): Color =
        if (interactiveState == CheckboxInteractiveState.Disabled) {
            labelDisabledColor
        } else {
            labelColor
        }

    companion object {

        @Composable
        fun colors(): CheckboxColors = with(LocalCarbonTheme.current) {
            CheckboxColors(
                borderColor = iconPrimary,
                borderDisabledColor = iconDisabled,
                borderReadOnlyColor = iconDisabled,
                borderErrorColor = supportError,

                backgroundCheckedColor = iconPrimary,
                backgroundDisabledCheckedColor = iconDisabled,

                checkmarkCheckedColor = iconInverse,
                checkmarkReadOnlyCheckedColor = iconPrimary,

                labelColor = textPrimary,
                labelDisabledColor = textDisabled,

                errorMessageTextColor = textError,
                errorIconColor = textError,
                errorIconInnerFillColor = iconInverse,

                warningMessageTextColor = textPrimary,
                warningIconColor = supportWarning,
                warningIconInnerFillColor = Color.Black,
            )
        }
    }
}
