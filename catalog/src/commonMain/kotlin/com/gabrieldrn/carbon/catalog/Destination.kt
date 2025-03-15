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

package com.gabrieldrn.carbon.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.buttons.ButtonDemoScreen
import com.gabrieldrn.carbon.catalog.checkbox.CheckboxDemoScreen
import com.gabrieldrn.carbon.catalog.contentswitcher.ContentSwitcherDemoScreen
import com.gabrieldrn.carbon.catalog.dropdown.DropdownDemoScreen
import com.gabrieldrn.carbon.catalog.dropdown.DropdownVariant
import com.gabrieldrn.carbon.catalog.loading.LoadingDemoScreen
import com.gabrieldrn.carbon.catalog.notification.NotificationDemoScreen
import com.gabrieldrn.carbon.catalog.progressbar.ProgressBarDemoScreen
import com.gabrieldrn.carbon.catalog.radiobutton.RadioButtonDemoScreen
import com.gabrieldrn.carbon.catalog.settings.SettingsScreen
import com.gabrieldrn.carbon.catalog.tab.TabListDemoScreen
import com.gabrieldrn.carbon.catalog.tag.TagDemoScreen
import com.gabrieldrn.carbon.catalog.textinput.TextInputDemoScreen
import com.gabrieldrn.carbon.catalog.toggle.ToggleDemoScreen
import org.jetbrains.compose.resources.DrawableResource

@Suppress("UndocumentedPublicClass", "UndocumentedPublicProperty")
enum class Destination(
    override val title: String,
    val illustration: DrawableResource? = null,
    override val route: String = "",
    val content: @Composable (Modifier) -> Unit = {}
) : BaseDestination {
    Home(
        title = "Carbon Design System",
        illustration = null,
        route = "home"
    ),
    Settings(
        title = "Settings",
        illustration = null,
        route = "settings",
        content = { modifier -> SettingsScreen(modifier = modifier) }
    ),
    Accordion("Accordion"),
    Breadcrumb("Breadcrumb"),
    Button(
        title = "Button",
        illustration = Res.drawable.tile_button,
        route = "button",
        content = { modifier -> ButtonDemoScreen(modifier = modifier) }
    ),
    Checkbox(
        title = "Checkbox",
        illustration = Res.drawable.tile_checkbox,
        route = "checkbox",
        content = { modifier -> CheckboxDemoScreen(modifier = modifier) }
    ),
    CodeSnippet("Code snippet"),
    ContentSwitcher(
        "Content switcher",
        illustration = Res.drawable.tile_content_switcher,
        route = "contentswitcher",
        content = { modifier -> ContentSwitcherDemoScreen(modifier = modifier) }
    ),
    DataTable("Data table"),
    DatePicker("Date picker"),
    Dropdown(
        title = "Dropdown",
        illustration = Res.drawable.tile_dropdown,
        route = "dropdown",
        content = { modifier ->
            DropdownDemoScreen(
                variant = DropdownVariant.Default,
                modifier = modifier
            )
        }
    ),
    FileUploader("File uploader"),
    Form("Form"),
    InlineLoading("Inline loading"),
    Link("Link"),
    List("List"),
    Loading(
        title = "Loading",
        illustration = Res.drawable.tile_loading,
        route = "loading",
        content = { modifier -> LoadingDemoScreen(modifier = modifier) }
    ),
    Modal("Modal"),
    MultiSelect(
        title = "Multi-select",
        illustration = Res.drawable.tile_mutliselect,
        route = "dropdown/multiselect",
        content = { modifier ->
            DropdownDemoScreen(
                variant = DropdownVariant.Multiselect,
                modifier = modifier
            )
        }
    ),
    Notification(
        "Notification",
        illustration = Res.drawable.tile_notification,
        route = "notification",
        content = { modifier -> NotificationDemoScreen(modifier = modifier) }
    ),
    NumberInput("Number input"),
    Pagination("Pagination"),
    ProgressBar(
        title = "Progress bar",
        illustration = Res.drawable.tile_progress_bar,
        route = "progressbar",
        content = { modifier -> ProgressBarDemoScreen(modifier = modifier) }
    ),
    ProgressIndicator("Progress indicator"),
    OverflowMenu("Overflow menu"),
    RadioButton(
        title = "Radio button",
        illustration = Res.drawable.tile_radiobutton,
        route = "radiobutton",
        content = { modifier -> RadioButtonDemoScreen(modifier = modifier) }
    ),
    Search("Search"),
    Select("Select"),
    Slider("Slider"),
    StructuredList("Structured list"),
    Tabs(
        title = "Tabs",
        illustration = Res.drawable.tile_tabs,
        route = "tabs",
        content = { modifier -> TabListDemoScreen(modifier = modifier) }
    ),
    Tag(
        title = "Tag",
        illustration = Res.drawable.tile_tag,
        route = "tag",
        content = { modifier -> TagDemoScreen(modifier = modifier) }
    ),
    TextInput(
        title = "Text input",
        illustration = Res.drawable.tile_text_input,
        route = "textinput",
        content = { modifier -> TextInputDemoScreen(modifier = modifier) }
    ),
    Tile("Tile"),
    Toggle(
        title = "Toggle",
        illustration = Res.drawable.tile_toggle,
        route = "toggle",
        content = { modifier -> ToggleDemoScreen(modifier = modifier) }
    ),
    Tooltip("Tooltip"),
    UIShell("UI shell");

    companion object {
        private val nonComponentDestinations = setOf(Home, Settings)

        val homeTilesDestinations = entries
            .filterNot { it in nonComponentDestinations }
            // Show first the components that have a demo activity
            .sortedByDescending { it.route.isNotEmpty() }
    }
}
