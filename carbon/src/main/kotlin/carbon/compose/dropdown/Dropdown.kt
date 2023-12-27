package carbon.compose.dropdown

import androidx.annotation.IntRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import carbon.compose.foundation.motion.Motion
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text

private val dropdownHeight = 40.dp

@Composable
public fun <K : Any> Dropdown(
    fieldPlaceholderText: String,
    optionSelected: String?,
    options: Map<K, String>,
    onOptionSelected: (K) -> Unit,
    modifier: Modifier = Modifier,
    @IntRange(from = 1) visibleItemsBeforeScroll: Int = 4
) {
    val colors = DropdownColors.colors()
    var showPopup by remember { mutableStateOf(false) }

    val popupHeight =
        options.size.coerceAtMost(visibleItemsBeforeScroll.coerceAtLeast(1)) *
            dropdownHeight +
            dropdownHeight * .5f

    val chevronRotation by animateFloatAsState(
        targetValue = if (showPopup) 180f else 0f,
        animationSpec = tween(
            durationMillis = Motion.Duration.moderate01,
            easing = Motion.Standard.productiveEasing
        ),
        label = "Chevron rotation"
    )

    BoxWithConstraints(modifier = modifier.height(40.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .background(colors.fieldBackgroundColor)
                .clickable { showPopup = !showPopup }
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = optionSelected ?: fieldPlaceholderText,
                style = CarbonTypography.bodyCompact01,
                modifier = Modifier.weight(1f)
            )
            Image(
                imageVector = chevronDownIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(colors.chevronIconColor),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .rotate(chevronRotation)
            )
        }
        Spacer(
            modifier = Modifier
                .background(color = colors.fieldBorderColor)
                .height(1.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
        if (showPopup) {
            // TODO Place popup on top of the field if the menu doesn't have enough space below it.
            Popup(
                popupPositionProvider = DropdownMenuPositionProvider,
                onDismissRequest = { showPopup = false }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .width(this@BoxWithConstraints.maxWidth)
                        .height(popupHeight)
                        // This should be a box shadow (-> 0 2px 6px 0 rgba(0,0,0,.2)). But compose
                        // doesn't provide the same API as CSS for shadows. A 3dp elevation is the
                        // best approximation that could be found for now.
                        .shadow(elevation = 3.dp)
                        .background(color = colors.menuOptionBackgroundColor)
                ) {
                    itemsIndexed(
                        items = options.entries.toList(),
                        key = { _, option -> option.key }
                    ) { index, option ->
                        DropdownMenuOption(
                            option = option,
                            onOptionSelected = onOptionSelected,
                            showDivider = index != 0,
                            colors = colors,
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun <K : Any> DropdownMenuOption(
    option: Map.Entry<K, String>,
    colors: DropdownColors,
    showDivider: Boolean,
    onOptionSelected: (K) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(onClick = { onOptionSelected(option.key) })
            .padding(horizontal = 16.dp)
    ) {
        if (showDivider) {
            DropdownMenuOptionDivider(
                colors.menuOptionBorderColor,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = option.value,
                style = CarbonTypography.bodyCompact01
            )
        }
    }
}

@Composable
private fun DropdownMenuOptionDivider(
    color: Color,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .background(color = color)
            .height(1.dp)
            .fillMaxWidth()
    )
}

private object DropdownMenuPositionProvider : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset = IntOffset(
        x = anchorBounds.left,
        y = anchorBounds.top + anchorBounds.height
    )
}
