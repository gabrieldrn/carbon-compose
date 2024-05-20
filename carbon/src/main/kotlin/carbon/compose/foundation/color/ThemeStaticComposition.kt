package carbon.compose.foundation.color

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import carbon.compose.Carbon

internal val carbonDefaultTheme = WhiteTheme

/**
 * A [staticCompositionLocalOf] that provides the current Carbon [Theme].
 */
public val LocalCarbonTheme: ProvidableCompositionLocal<Theme> =
    staticCompositionLocalOf { WhiteTheme }

/**
 * A [staticCompositionLocalOf] that provides a Carbon inline [Theme], usually used for components
 * that need to be themed differently from the rest of the app, such as UI Shell components.
 */
public val LocalCarbonInlineTheme: ProvidableCompositionLocal<Theme> =
    staticCompositionLocalOf { Gray100Theme }

/**
 * A [staticCompositionLocalOf] that provides a [Layer] token. Layering tokens are explicit tokens
 * used to manually map the layering model onto components.
 */
public val LocalCarbonLayer: ProvidableCompositionLocal<Layer> =
    staticCompositionLocalOf { Layer.Layer00 }

/**
 * Automatically provides an upper layer to the composition, based on the current layer.
 * @param content Your UI content.
 */
@Composable
public fun CarbonLayer(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        value = LocalCarbonLayer provides Carbon.layer.next(),
        content = content
    )
}

/**
 * Provides a [Layer] to following composition.
 * @param layer The layer to provide.
 * @param content Your UI content.
 */
@Composable
public fun CarbonLayer(
    layer: Layer,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        value = LocalCarbonLayer provides layer,
        content = content
    )
}
