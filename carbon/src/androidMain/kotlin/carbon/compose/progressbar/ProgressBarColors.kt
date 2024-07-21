package carbon.compose.progressbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme

@Immutable
internal class ProgressBarColors private constructor(private val theme: Theme) {

    @Composable
    fun trackColor(state: ProgressBarState): State<Color> =
        rememberUpdatedState(
            when (state) {
                ProgressBarState.Active -> theme.borderInteractive
                ProgressBarState.Success -> theme.supportSuccess
                ProgressBarState.Error -> theme.supportError
            }
        )

    @Composable
    fun iconColor(state: ProgressBarState): State<Color> =
        rememberUpdatedState(
            when (state) {
                ProgressBarState.Active -> Color.Unspecified
                ProgressBarState.Success -> theme.supportSuccess
                ProgressBarState.Error -> theme.supportError
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProgressBarColors

        return theme == other.theme
    }

    override fun hashCode(): Int = theme.hashCode()

    companion object {

        @Composable
        internal fun colors() = ProgressBarColors(Carbon.theme)
    }
}
