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

package com.gabrieldrn.docparser.color.model.colortokens

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val notificationActionHover: ColorDefinition,
    val notificationActionTertiaryInverse: ColorDefinition,
    val notificationActionTertiaryInverseActive: ColorDefinition,
    val notificationActionTertiaryInverseHover: ColorDefinition,
    val notificationActionTertiaryInverseText: ColorDefinition,
    val notificationActionTertiaryInverseTextOnColorDisabled: ColorDefinition,
    val notificationBackgroundError: ColorDefinition,
    val notificationBackgroundInfo: ColorDefinition,
    val notificationBackgroundSuccess: ColorDefinition,
    val notificationBackgroundWarning: ColorDefinition
)
