package carbon.compose.semantics

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

public object CarbonSemanticsProperties {

    public val ReadOnly: SemanticsPropertyKey<Boolean> = SemanticsPropertyKey("ReadOnly")

}

/**
 * Whether the node is read-only.
 */
public fun SemanticsPropertyReceiver.readOnly() {
    this[CarbonSemanticsProperties.ReadOnly] = true
}
