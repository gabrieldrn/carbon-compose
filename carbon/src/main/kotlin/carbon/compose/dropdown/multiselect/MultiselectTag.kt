package carbon.compose.dropdown.multiselect

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import carbon.compose.dropdown.base.DropdownFieldContentId
import carbon.compose.dropdown.base.DropdownTestTags
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.color.Theme
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.Text
import carbon.compose.icons.CloseIcon

@Composable
internal fun DropdownMultiselectTag(
    count: Int,
    onCloseTagClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Row(
        modifier = modifier
            .height(SpacingScale.spacing06)
            .background(
                color = LocalCarbonTheme.current.backgroundInverse,
                shape = RoundedCornerShape(100f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onCloseTagClick
            )
            .testTag(DropdownTestTags.FIELD_MULTISELECT_TAG)
            .layoutId(DropdownFieldContentId.MULTISELECT_TAG),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = count.toString(),
            color = LocalCarbonTheme.current.textInverse,
            modifier = Modifier.padding(start = SpacingScale.spacing03)
        )
        Spacer(modifier = Modifier.width(SpacingScale.spacing01))
        Box(
            modifier = Modifier
                .size(SpacingScale.spacing06)
                .indication(
                    indication = MultiselectTagIndication(),
                    interactionSource = interactionSource
                )
        ) {
            CloseIcon(
                tint = LocalCarbonTheme.current.iconInverse,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

private class MultiselectTagIndication : Indication {

    class MultiselectTagIndicationInstance(private val theme: Theme) : IndicationInstance {

        var isHovered: Boolean by mutableStateOf(false)

        override fun ContentDrawScope.drawIndication() {
            if (isHovered) {
                inset(2f.dp.toPx()) {
                    drawCircle(theme.backgroundHover)
                }
            }

            drawContent()
        }
    }

    @Composable
    override fun rememberUpdatedInstance(
        interactionSource: InteractionSource
    ): IndicationInstance {
        val theme = LocalCarbonTheme.current

        val instance = remember(theme) {
            MultiselectTagIndicationInstance(theme)
        }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is HoverInteraction.Enter -> instance.isHovered = true
                    is HoverInteraction.Exit -> instance.isHovered = false
                    else -> {}
                }
            }
        }

        return instance
    }
}
