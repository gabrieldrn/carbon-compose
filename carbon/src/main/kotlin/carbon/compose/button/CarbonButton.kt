package carbon.compose.button

/**
 * Variants of the Carbon Button.
 *
 * Each button variant has a particular function and its design signals that function to the user.
 * It is therefore very important that the different variants are implemented consistently across
 * products, so that they message the correct actions.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 */
public enum class CarbonButton {

    /**
     * For the principal call to action on the page. Primary buttons should only appear once per
     * screen (not including the application header, modal dialog, or side panel).
     */
    Primary,

    /**
     * For secondary actions on each page. Secondary buttons can only be used in conjunction with a
     * primary button. As part of a pair, the secondary button’s function is to perform the negative
     * action of the set, such as "Cancel" or "Back". Do not use a secondary button in isolation and
     * do not use a secondary button for a positive action.
     */
    Secondary,

    /**
     * For less prominent, and sometimes independent, actions. Tertiary buttons can be used in
     * isolation or paired with a primary button when there are multiple calls to action. Tertiary
     * buttons can also be used for sub-tasks on a page where a primary button for the main and
     * final action is present.
     */
    Tertiary,

    /**
     * For the least pronounced actions; often used in conjunction with a primary button. In a
     * situation such as a progress flow, a ghost button may be paired with a primary and secondary
     * button set, where the primary button is for forward action, the secondary button is for
     * "Back", and the ghost button is for "Cancel".
     */
    Ghost,

    /**
     * Same as [Primary], but for actions that could have destructive effects on the user’s data
     * (for example, delete or remove).
     */
    PrimaryDanger,

    /**
     * Same as [Secondary], but for actions that could have destructive effects on the user’s data
     * (for example, delete or remove).
     */
    TertiaryDanger,

    /**
     * Same as [Ghost], but for actions that could have destructive effects on the user’s data (for
     * example, delete or remove).
     */
    GhostDanger
}
