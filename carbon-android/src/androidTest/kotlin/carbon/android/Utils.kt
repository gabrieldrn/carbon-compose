package carbon.android

import androidx.compose.ui.test.SemanticsNodeInteractionCollection

fun SemanticsNodeInteractionCollection.toList() = fetchSemanticsNodes().indices.map(::get)
