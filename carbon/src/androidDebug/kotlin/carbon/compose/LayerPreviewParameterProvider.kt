package carbon.compose

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import carbon.compose.foundation.color.Layer

internal class LayerPreviewParameterProvider : PreviewParameterProvider<Layer> {
    override val values: Sequence<Layer> = Layer.values().asSequence()
}
