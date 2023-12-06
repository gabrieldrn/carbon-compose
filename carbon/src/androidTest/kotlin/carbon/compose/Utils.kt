package carbon.compose

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection

val SemanticsNodeInteraction.width get() = with(fetchSemanticsNode()) {
    with(layoutInfo.density) { layoutInfo.width.toDp() }
}

fun SemanticsNodeInteractionCollection.toList() = fetchSemanticsNodes().indices.map(::get)
