package carbon.compose.foundation.selectable

/**
 * Represents the possible interactive states of a selectable.
 * Selectable component are for example a checkbox or a radio button.
 */
public sealed class SelectableInteractiveState {

    /**
     * Default state, the component is enabled and can be interacted with.
     */
    public data object Default : SelectableInteractiveState()

    /**
     * Disabled state, the component is disabled and cannot be interacted with.
     */
    public data object Disabled : SelectableInteractiveState()

    /**
     * Read-only state, the component cannot be interacted with but stays focusable.
     */
    public data object ReadOnly : SelectableInteractiveState()

    /**
     * Error state, the component is enabled and be interacted with. An error message is displayed
     * next to the component.
     * @param errorMessage The error message to display.
     */
    public data class Error(val errorMessage: String) : SelectableInteractiveState()

    /**
     * Warning state, the component is enabled and be interacted with. A warning message is
     * displayed next to the component.
     * @param warningMessage The warning message to display.
     */
    public data class Warning(val warningMessage: String) : SelectableInteractiveState()
}
