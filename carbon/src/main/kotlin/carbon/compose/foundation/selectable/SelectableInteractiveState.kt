package carbon.compose.foundation.selectable

/**
 * Represents the possible interactive states of a selectable.
 * Selectable component are for example a checkbox or a radio button.
 */
public enum class SelectableInteractiveState {

    /**
     * Default state, the component is enabled and can be interacted with.
     */
    Default,

    /**
     * Disabled state, the component is disabled and cannot be interacted with.
     */
    Disabled,

    /**
     * Read-only state, the component cannot be interacted with but stays focusable.
     */
    ReadOnly,

    /**
     * Error state, the component is enabled and be interacted with. An error message is displayed
     * next to the component.
     */
    Error,

    /**
     * Warning state, the component is enabled and be interacted with. A warning message is
     * displayed next to the component.
     */
    Warning
}
