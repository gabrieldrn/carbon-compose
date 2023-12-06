package carbon.compose.semantics

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.SemanticsModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.stateDescription
import carbon.compose.R

/**
 * Configures a component to be stated as "read-only" and defines a description for semantics and
 * accessibility. The description can be overridden with the string resource
 * [R.string.carbon_interactive_state_read_only].
 */
public fun Modifier.readOnly(): Modifier = composed {
    this.then(
        ReadOnlyElement(description = stringResource(R.string.carbon_interactive_state_read_only))
    )
}

private class ReadOnlyElement(
    private val description: String
) : ModifierNodeElement<ReadOnlyNode>() {

    override fun create(): ReadOnlyNode = ReadOnlyNode(description)

    override fun update(node: ReadOnlyNode) {
        node.update(description)
    }

    override fun InspectorInfo.inspectableProperties() {
        properties["readOnlyDescription"] = description
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReadOnlyElement

        return description == other.description
    }

    override fun hashCode(): Int = description.hashCode()
}

private class ReadOnlyNode(
    var description: String
) : SemanticsModifierNode, Modifier.Node() {

    override val shouldMergeDescendantSemantics = true

    fun update(readOnlyDescription: String) {
        this.description = readOnlyDescription
    }

    override fun SemanticsPropertyReceiver.applySemantics() {
        readOnly()
        disabled()
        stateDescription = description
        onClick(null, null)
    }
}
