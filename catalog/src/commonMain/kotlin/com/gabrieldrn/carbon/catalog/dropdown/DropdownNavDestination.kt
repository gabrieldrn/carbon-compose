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

package com.gabrieldrn.carbon.catalog.dropdown

import com.gabrieldrn.carbon.catalog.BaseDestination
import com.gabrieldrn.carbon.catalog.Destination
import org.jetbrains.compose.resources.DrawableResource

@Suppress("UndocumentedPublicClass", "UndocumentedPublicProperty")
enum class DropdownNavDestination(
    override val title: String,
    val illustration: DrawableResource? = null,
    override val route: String = "",
) : BaseDestination {
    Home("Dropdown", null, "dropdown_home"),
    Default("Dropdown", null, "dropdown/default"),
    MultiSelect("Multi-select", null, Destination.MultiSelect.route),
}
