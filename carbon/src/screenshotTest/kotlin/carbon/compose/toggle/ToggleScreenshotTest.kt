package carbon.compose.toggle

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import carbon.compose.CarbonDesignSystem

class ToggleScreenshotTest {

    @Preview(showBackground = true)
    @Composable
    private fun DefaultTogglePreview(
        @PreviewParameter(DefaultToggleParametersProvider::class) params: DefaultToggleParams
    ) {
        CarbonDesignSystem {
            Toggle(
                isToggled = params.isToggled,
                onToggleChange = {},
                label = params.label,
                actionText = "Action text",
                isEnabled = params.isEnabled,
                isReadOnly = params.isReadOnly,
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun SmallTogglePreview(
        @PreviewParameter(SmallToggleParametersProvider::class) params: SmallToggleParams
    ) {
        CarbonDesignSystem {
            SmallToggle(
                isToggled = params.isToggled,
                onToggleChange = {},
                actionText = "Action text",
                isEnabled = params.isEnabled,
                isReadOnly = params.isReadOnly,
            )
        }
    }

    class DefaultToggleParametersProvider : PreviewParameterProvider<DefaultToggleParams> {
        override val values: Sequence<DefaultToggleParams>
            get() {
                val params = mutableListOf<DefaultToggleParams>()

                listOf(true, false).forEach { isToggled ->
                    listOf("Label", "").forEach { label ->
                        listOf(true, false).forEach { isEnabled ->
                            listOf(true, false).forEach { isReadOnly ->
                                params.add(
                                    DefaultToggleParams(
                                        isToggled,
                                        label,
                                        isEnabled,
                                        isReadOnly,
                                    )
                                )
                            }
                        }
                    }
                }

                return params.asSequence()
            }
    }

    class SmallToggleParametersProvider : PreviewParameterProvider<SmallToggleParams> {
        override val values: Sequence<SmallToggleParams>
            get() {
                val params = mutableListOf<SmallToggleParams>()

                listOf(true, false).forEach { isToggled ->
                    listOf(true, false).forEach { isEnabled ->
                        listOf(true, false).forEach { isReadOnly ->
                            params.add(
                                SmallToggleParams(
                                    isToggled,
                                    isEnabled,
                                    isReadOnly,
                                )
                            )
                        }
                    }
                }

                return params.asSequence()
            }
    }

    data class DefaultToggleParams(
        val isToggled: Boolean,
        val label: String,
        val isEnabled: Boolean,
        val isReadOnly: Boolean
    )

    data class SmallToggleParams(
        val isToggled: Boolean,
        val isEnabled: Boolean,
        val isReadOnly: Boolean
    )
}
