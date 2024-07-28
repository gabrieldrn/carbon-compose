package carbon.compose.semantics

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

/**
 * Carbon design system semantics properties. They follow the same principles as the ones from
 * [androidx.compose.ui.semantics.SemanticsProperties].
 *
 * @see [androidx.compose.ui.semantics.SemanticsProperties]
 */
public object CarbonSemanticsProperties {

    /**
     * @see [readOnly]
     */
    public val ReadOnly: SemanticsPropertyKey<Boolean> =
        SemanticsPropertyKey("ReadOnly")

    /**
     * @see [imageVectorName]
     */
    internal val ImageVectorName: SemanticsPropertyKey<String> =
        SemanticsPropertyKey("ImageVectorName")

}

/**
 * Whether the node is read-only.
 */
public fun SemanticsPropertyReceiver.readOnly() {
    this[CarbonSemanticsProperties.ReadOnly] = true
}

/**
 * Mark semantic node that contains an icon identified by its name.
 */
internal fun SemanticsPropertyReceiver.imageVectorName(name: String) {
    this[CarbonSemanticsProperties.ImageVectorName] = name
}
