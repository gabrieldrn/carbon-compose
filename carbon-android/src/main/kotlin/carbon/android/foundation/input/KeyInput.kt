package carbon.android.foundation.input

import android.view.KeyEvent.KEYCODE_DPAD_CENTER
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.KeyEvent.KEYCODE_NUMPAD_ENTER
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type

private val KeyEvent.isEnter: Boolean
    get() = when (key.nativeKeyCode) {
        KEYCODE_DPAD_CENTER, KEYCODE_ENTER, KEYCODE_NUMPAD_ENTER -> true
        else -> false
    }

internal fun Modifier.onEnterKeyEvent(
    onEnterKey: () -> Unit
): Modifier = this.onKeyEvent { keyEvent ->
    if (keyEvent.type == KeyEventType.KeyUp && keyEvent.isEnter) {
        onEnterKey()
        true
    } else {
        false
    }
}
