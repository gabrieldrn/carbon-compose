package carbon.compose.radiobutton

/**
 * Represents the possible interactive states of a [Checkbox].
 */
public enum class RadioButtonInteractiveState {

    /**
     * Default state, the checkbox is enabled and can be interacted with.
     */
    Default,

    /**
     * Disabled state, the checkbox is disabled and cannot be interacted with.
     */
    Disabled,

    /**
     * Read-only state, the checkbox cannot be interacted with but stays focusable.
     */
    ReadOnly,

    /**
     * Error state, the checkbox is enabled and be interacted with. An error message is displayed
     * below the checkbox.
     */
    Error,

    /**
     * Warning state, the checkbox is enabled and be interacted with. A warning message is displayed
     * below the checkbox.
     */
    Warning
}
