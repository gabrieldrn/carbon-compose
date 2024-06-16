package carbon.compose.textinput

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.foundation.interaction.FocusIndication
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text
import carbon.compose.icons.WarningAltIcon
import carbon.compose.icons.WarningIcon
import carbon.compose.semantics.readOnly
import carbon.compose.textinput.TextInputState.Companion.isFocusable

@Composable
public fun TextInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    helperText: String = "",
    state: TextInputState = TextInputState.Enabled,
    size: TextInputSize = TextInputSize.Large,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val theme = Carbon.theme
    val colors = TextInputColors.colors()

    val fieldTextColor by colors.fieldTextColor(state = state)
    val fieldTextStyle by remember(fieldTextColor) {
        mutableStateOf(CarbonTypography.bodyCompact01.copy(color = fieldTextColor))
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = CarbonTypography.label01,
            color = colors.labelTextColor(state = state).value,
            modifier = Modifier.padding(bottom = SpacingScale.spacing03)
        )

        Box(
            modifier = Modifier
                .indication(
                    interactionSource = interactionSource,
                    indication = FocusIndication()
                )
                .focusable(
                    enabled = state.isFocusable,
                    interactionSource = interactionSource
                )
                .height(size.height)
                .background(color = colors.fieldBackgroundColor(state = state).value)
                .then(
                    if (state == TextInputState.Error) {
                        Modifier.border(
                            width = SpacingScale.spacing01,
                            color = theme.supportError
                        )
                    } else {
                        Modifier
                    }
                )
                .then(
                    when (state) {
                        TextInputState.Disabled -> Modifier.semantics { disabled() }
                        TextInputState.ReadOnly -> Modifier.readOnly(
                            role = null,
                            interactionSource = interactionSource,
                            mergeDescendants = true
                        )
                        else -> Modifier
                    }
                )
                .semantics(mergeDescendants = true) {
                    stateDescription = helperText
                }
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.align(Alignment.CenterStart),
                enabled = state != TextInputState.Disabled,
                readOnly = state == TextInputState.ReadOnly,
                textStyle = fieldTextStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = true,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    FieldContent(
                        value = value,
                        placeholderText = placeholderText,
                        colors = colors,
                        innerTextField = innerTextField,
                        state = state
                    )
                }
            )

            if (state != TextInputState.Disabled) {
                Spacer(
                    modifier = Modifier
                        .background(color = colors.fieldBorderColor(state = state).value)
                        .height(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .testTag("TODO")
                )
            }
        }

        if (helperText.isNotEmpty()) {
            Text(
                text = helperText,
                style = CarbonTypography.helperText01,
                color = colors.helperTextColor(state = state).value,
                modifier = Modifier
                    .padding(top = SpacingScale.spacing02)
                    .testTag("TODO")
            )
        }
    }
}

@Composable
private fun FieldContent(
    value: String,
    placeholderText: String,
    colors: TextInputColors,
    state: TextInputState,
    innerTextField: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = SpacingScale.spacing05)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            innerTextField()
            if (value.isEmpty()) {
                Text(
                    text = placeholderText,
                    style = CarbonTypography.bodyCompact01,
                    color = colors.placeholderTextColor(state = state).value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("TODO")
                )
            }
        }
        when (state) {
            TextInputState.Error -> WarningIcon(
                modifier = Modifier.padding(start = SpacingScale.spacing05)
            )
            TextInputState.Warning -> WarningAltIcon(
                modifier = Modifier.padding(start = SpacingScale.spacing05)
            )
            else -> {}
        }
    }
}
