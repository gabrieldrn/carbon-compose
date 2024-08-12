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

package com.gabrieldrn.carbon.progressbar

import androidx.compose.runtime.Immutable

/**
 * The [ProgressBar] is offered in two different sizes—big (8px) and small (4px). The big progress
 * bar height is typically used when there is large amounts of space on a page. The small progress
 * bar height is commonly used when space is restricted and can be placed within cards, data tables,
 * or side panels.
 *
 * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
 */
@Immutable
public enum class ProgressBarSize {
    Big, Small
}
