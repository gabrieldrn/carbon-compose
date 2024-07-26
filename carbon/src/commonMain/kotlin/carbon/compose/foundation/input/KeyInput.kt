package carbon.compose.foundation.input

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type

private val enterKeys = arrayOf(Key.Enter, Key.NumPadEnter)

internal fun Modifier.onEnterKeyEvent(
    onEnterKey: () -> Unit
): Modifier = this.onKeyEvent { keyEvent ->
    if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key in enterKeys) {
        onEnterKey()
        true
    } else {
        false
    }
}
