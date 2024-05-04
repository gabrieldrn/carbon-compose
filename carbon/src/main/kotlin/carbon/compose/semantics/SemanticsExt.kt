package carbon.compose.semantics

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert

/**
 * Returns whether the node is read-only and checks the following:
 * - The component is disabled.
 * - The component is focused.
 * - The component has read-only property.
 *
 * @see CarbonSemanticsProperties.ReadOnly
 */
public fun isReadOnly(): SemanticsMatcher = SemanticsMatcher(
    description = "is read-only: is disabled, is focusable, has read-only property",
    matcher = {
        SemanticsProperties.Disabled in it.config &&
            SemanticsProperties.Focused in it.config &&
            it.config.getOrElseNullable(CarbonSemanticsProperties.ReadOnly) { null } == true
    },
)

/**
 * Asserts that the node is read-only.
 */
public fun SemanticsNodeInteraction.assertIsReadOnly(
    messagePrefixOnError: (() -> String)? = null
): SemanticsNodeInteraction = assert(
    matcher = isReadOnly(),
    messagePrefixOnError = messagePrefixOnError
)
