package carbon.compose.textinput

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.spacing.SpacingScale

@Preview
@Composable
private fun PasswordInputPreview(
    @PreviewParameter(TextInputStatePreviewParameterProvider::class) state: TextInputState
) {
    var text by remember {
        mutableStateOf("S0mePa55word%")
    }
    var passwordHidden by remember {
        mutableStateOf(false)
    }

    CarbonDesignSystem {
        PasswordInput(
            label = "Label",
            value = text,
            passwordHidden = passwordHidden,
            onValueChange = { text = it },
            onPasswordHiddenChange = { passwordHidden = it },
            modifier = Modifier.padding(SpacingScale.spacing03),
            placeholderText = "Placeholder",
            helperText = state.name,
            state = state,
        )
    }
}
