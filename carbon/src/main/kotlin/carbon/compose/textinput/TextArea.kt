package carbon.compose.textinput

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.foundation.text.CarbonTypography

/**
 * # Text area
 *
 * Use a text area when the expected user input is more than a few words and could span multiple
 * lines. Text area has several unique functionalities not included in the text input, like the
 * resize handle, word counter and character counter.
 *
 * ## Implementation note:
 * As per Carbon's documentation, the text area component has a minimum height of 40px/2.5rem, but
 * regarding accessibility on Android, this value is revised to the Large text input size, as it
 * complies with accessibility.
 *
 * (From [Text input documentation](https://carbondesignsystem.com/components/text-input/usage/))
 *
 * @param label Text that informs the user about the content they need to enter in the field.
 * @param value The input [String] text to be shown in the text input.
 * @param onValueChange The callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier Optional [Modifier] for this text input.
 * @param placeholderText Optionnal text that provides hints or examples of what to enter.
 * @param helperText Optional helper text is pertinent information that assists the user in
 * correctly completing a field. It is often used to explain the correct data format.
 * @param state The interactive state of the text input.
 * @param counter Word/character counter values to be displayed next to the label. The first value
 * is the current count and the second value is the maximum value.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param visualTransformation The visual transformation filter for changing the visual
 * representation of the input. By default no visual transformation is applied.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 */
@Composable
public fun TextArea(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    helperText: String = "",
    state: TextInputState = TextInputState.Enabled,
    counter: Pair<Int, Int>? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val theme = Carbon.theme
    val colors = TextInputColors.colors()

    val fieldTextColor by colors.fieldTextColor(state = state)
    val fieldTextStyle by remember(fieldTextColor) {
        mutableStateOf(CarbonTypography.bodyCompact01.copy(color = fieldTextColor))
    }

    TextInputRoot(
        state = state,
        label = label,
        helperText = helperText,
        colors = colors,
        counter = counter,
        field = {
            TextInputField(
                value = value,
                onValueChange = onValueChange,
                placeholderText = placeholderText,
                helperText = helperText,
                state = state,
                theme = theme,
                colors = colors,
                fieldTextStyle = fieldTextStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = false,
                maxLines = maxLines,
                minLines = minLines,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                modifier = Modifier.sizeIn(minHeight = TEXT_INPUT_HEIGHT_LARGE_DP.dp)
            )
        },
        modifier = modifier
    )
}
