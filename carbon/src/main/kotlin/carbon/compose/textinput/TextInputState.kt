package carbon.compose.textinput

public enum class TextInputState {

    Enabled, Warning, Error, Disabled, ReadOnly;

    public companion object {

        internal val TextInputState.isFocusable: Boolean
            get() = this != Disabled
    }
}
