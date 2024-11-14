/*
 * Copyright 2024 Gabriel Derrien
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

package com.gabrieldrn.codegen.color.model.colortokens2

import kotlinx.serialization.Serializable

@Serializable
data class Theme(
    val aiColors: AiColors,
    val background: String,
    val backgroundActive: String,
    val backgroundBrand: String,
    val backgroundHover: String,
    val backgroundInverse: String,
    val backgroundInverseHover: String,
    val backgroundSelected: String,
    val backgroundSelectedHover: String,
    val borderDisabled: String,
    val borderInteractive: String,
    val borderInverse: String,
    val borderStrong01: String,
    val borderStrong02: String,
    val borderStrong03: String,
    val borderSubtle00: String,
    val borderSubtle01: String,
    val borderSubtle02: String,
    val borderSubtle03: String,
    val borderSubtleSelected01: String,
    val borderSubtleSelected02: String,
    val borderSubtleSelected03: String,
    val borderTile01: String,
    val borderTile02: String,
    val borderTile03: String,
    val buttonColors: ButtonColors,
    val chatColors: ChatColors,
    val field01: String,
    val field02: String,
    val field03: String,
    val fieldHover01: String,
    val fieldHover02: String,
    val fieldHover03: String,
    val focus: String,
    val focusInset: String,
    val focusInverse: String,
    val highlight: String,
    val iconDisabled: String,
    val iconInteractive: String,
    val iconInverse: String,
    val iconOnColor: String,
    val iconOnColorDisabled: String,
    val iconPrimary: String,
    val iconSecondary: String,
    val interactive: String,
    val layer01: String,
    val layer02: String,
    val layer03: String,
    val layerAccent01: String,
    val layerAccent02: String,
    val layerAccent03: String,
    val layerAccentActive01: String,
    val layerAccentActive02: String,
    val layerAccentActive03: String,
    val layerAccentHover01: String,
    val layerAccentHover02: String,
    val layerAccentHover03: String,
    val layerActive01: String,
    val layerActive02: String,
    val layerActive03: String,
    val layerHover01: String,
    val layerHover02: String,
    val layerHover03: String,
    val layerSelected01: String,
    val layerSelected02: String,
    val layerSelected03: String,
    val layerSelectedDisabled: String,
    val layerSelectedHover01: String,
    val layerSelectedHover02: String,
    val layerSelectedHover03: String,
    val layerSelectedInverse: String,
    val linkInverse: String,
    val linkInverseActive: String,
    val linkInverseHover: String,
    val linkInverseVisited: String,
    val linkPrimary: String,
    val linkPrimaryHover: String,
    val linkSecondary: String,
    val linkVisited: String,
    val notificationColors: NotificationColors,
    val overlay: String,
    val shadow: String,
    val skeletonBackground: String,
    val skeletonElement: String,
    val supportCautionMajor: String,
    val supportCautionMinor: String,
    val supportCautionUndefined: String,
    val supportError: String,
    val supportErrorInverse: String,
    val supportInfo: String,
    val supportInfoInverse: String,
    val supportSuccess: String,
    val supportSuccessInverse: String,
    val supportWarning: String,
    val supportWarningInverse: String,
    val tagColors: TagColors,
    val textDisabled: String,
    val textError: String,
    val textHelper: String,
    val textInverse: String,
    val textOnColor: String,
    val textOnColorDisabled: String,
    val textPlaceholder: String,
    val textPrimary: String,
    val textSecondary: String,
    val toggleOff: String
)