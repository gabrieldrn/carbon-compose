/*
 * Copyright 2026 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.carbon.loading.inlineloading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text
import com.gabrieldrn.carbon.icons.CheckmarkFilledIcon
import com.gabrieldrn.carbon.icons.ErrorFilledIcon
import com.gabrieldrn.carbon.loading.SmallLoading

private val inlineLoadingIndicatorSize = 16.dp

/**
 * # Inline loading
 *
 * Inline loading provides immediate feedback that a short-running action is being processed.
 *
 * (From [Inline loading documentation](https://carbondesignsystem.com/components/inline-loading/usage))
 *
 * @param modifier The modifier to apply to the component.
 * @param status The visual status to display.
 * @param label Optional visible text describing the current status.
 * @param contentDescription Optional accessibility description announced when [label] is omitted.
 */
@Composable
public fun InlineLoading(
    modifier: Modifier = Modifier,
    status: InlineLoadingStatus = InlineLoadingStatus.Active,
    label: String? = null,
    contentDescription: String? = null,
) {
    Row(
        modifier = modifier
            .inlineLoadingContainerSemantics(status = status)
            .testTag(InlineLoadingTestTags.CONTAINER),
        horizontalArrangement = Arrangement.spacedBy(SpacingScale.spacing03),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (status != InlineLoadingStatus.Inactive) {
            InlineLoadingIndicator(
                status = status,
                modifier = Modifier
                    .inlineLoadingIndicatorSemantics(
                        exposeDescription = label == null,
                        contentDescription = contentDescription
                            ?: status.defaultContentDescription()
                    )
                    .testTag(InlineLoadingTestTags.ICON)
            )
        }

        if (label != null) {
            Text(
                text = label,
                style = Carbon.typography.bodyCompact01,
                color = Carbon.theme.textSecondary,
                maxLines = 1,
                softWrap = false,
                modifier = Modifier.testTag(InlineLoadingTestTags.TEXT)
            )
        }
    }
}

private fun Modifier.inlineLoadingContainerSemantics(
    status: InlineLoadingStatus
): Modifier = semantics(mergeDescendants = true) {
    liveRegion = when (status) {
        InlineLoadingStatus.Inactive,
        InlineLoadingStatus.Active -> LiveRegionMode.Polite
        InlineLoadingStatus.Finished,
        InlineLoadingStatus.Error -> LiveRegionMode.Assertive
    }
}

private fun Modifier.inlineLoadingIndicatorSemantics(
    exposeDescription: Boolean,
    contentDescription: String?
): Modifier = if (exposeDescription && contentDescription != null) {
    semantics {
        this.contentDescription = contentDescription
    }
} else {
    this
}

@Composable
private fun InlineLoadingIndicator(
    status: InlineLoadingStatus,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (status) {
            InlineLoadingStatus.Inactive -> Unit
            InlineLoadingStatus.Active -> SmallLoading()
            InlineLoadingStatus.Finished -> CheckmarkFilledIcon(
                size = inlineLoadingIndicatorSize,
                tint = Carbon.theme.supportSuccess
            )
            InlineLoadingStatus.Error -> ErrorFilledIcon(
                size = inlineLoadingIndicatorSize,
                tint = Carbon.theme.supportError
            )
        }
    }
}

private fun InlineLoadingStatus.defaultContentDescription(): String? = when (this) {
    InlineLoadingStatus.Inactive -> null
    InlineLoadingStatus.Active -> "loading"
    InlineLoadingStatus.Finished -> "finished"
    InlineLoadingStatus.Error -> "error"
}

private class InlineLoadingStatusParameterProvider : PreviewParameterProvider<InlineLoadingStatus> {
    override val values: Sequence<InlineLoadingStatus> = InlineLoadingStatus.entries.asSequence()
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InlineLoadingPreview(
    @PreviewParameter(InlineLoadingStatusParameterProvider::class) status: InlineLoadingStatus
) {
    CarbonDesignSystem {
        InlineLoading(
            status = status,
            label = when (status) {
                InlineLoadingStatus.Inactive -> "Inactive"
                InlineLoadingStatus.Active -> "Loading"
                InlineLoadingStatus.Finished -> "Finished"
                InlineLoadingStatus.Error -> "Error"
            }
        )
    }
}
