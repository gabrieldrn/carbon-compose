package carbon.compose.catalog.home

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import carbon.compose.catalog.R
import carbon.compose.catalog.buttons.ButtonDemoScreen
import carbon.compose.catalog.checkbox.CheckboxDemoScreen
import carbon.compose.catalog.dropdown.BaseDestination
import carbon.compose.catalog.dropdown.DropdownDemoScreen
import carbon.compose.catalog.dropdown.DropdownVariant
import carbon.compose.catalog.radiobutton.LoadingDemoScreen
import carbon.compose.catalog.radiobutton.RadioButtonDemoScreen
import carbon.compose.catalog.textinput.TextInputDemoScreen
import carbon.compose.catalog.toggle.ToggleDemoScreen
import carbon.compose.catalog.progressbar.ProgressBarDemoScreen

@Suppress("UndocumentedPublicClass", "UndocumentedPublicProperty")
enum class Destination(
    override val title: String,
    @DrawableRes val illustration: Int? = null,
    override val route: String = "",
    val content: @Composable () -> Unit = {}
) : BaseDestination {
    Home(
        title = "Carbon Design System",
        illustration = null,
        route = "home"
    ),
    Accordion("Accordion"),
    Breadcrumb("Breadcrumb"),
    Button(
        title = "Button",
        illustration = R.drawable.tile_button,
        route = "button",
        content = { ButtonDemoScreen() }
    ),
    Checkbox(
        title = "Checkbox",
        illustration = R.drawable.tile_checkbox,
        route = "checkbox",
        content = { CheckboxDemoScreen() }
    ),
    CodeSnippet("Code snippet"),
    ContentSwitcher("Content switcher"),
    DataTable("Data table"),
    DatePicker("Date picker"),
    Dropdown(
        title = "Dropdown",
        illustration = R.drawable.tile_dropdown,
        route = "dropdown"
    ),
    FileUploader("File uploader"),
    Form("Form"),
    InlineLoading("Inline loading"),
    Link("Link"),
    List("List"),
    Loading(
        title = "Loading",
        illustration = R.drawable.tile_loading,
        route = "loading",
        content = { LoadingDemoScreen() }
    ),
    Modal("Modal"),
    MultiSelect(
        title = "Multi-select",
        illustration = R.drawable.tile_mutliselect,
        route = "dropdown/multiselect",
        content = { DropdownDemoScreen(DropdownVariant.Multiselect) }
    ),
    Notification("Notification"),
    NumberInput("Number input"),
    Pagination("Pagination"),
    ProgressBar(
        title = "Progress bar",
        illustration = R.drawable.tile_progress_bar,
        route = "progressbar",
        content = { ProgressBarDemoScreen() }
    ),
    ProgressIndicator("Progress indicator"),
    OverflowMenu("Overflow menu"),
    RadioButton(
        title = "Radio button",
        illustration = R.drawable.tile_radiobutton,
        route = "radiobutton",
        content = { RadioButtonDemoScreen() }
    ),
    Search("Search"),
    Select("Select"),
    Slider("Slider"),
    StructuredList("Structured list"),
    Tabs("Tabs"),
    Tag("Tag"),
    TextInput(
        title = "Text input",
        illustration = R.drawable.tile_text_input,
        route = "textinput",
        content = { TextInputDemoScreen() }
    ),
    Tile("Tile"),
    Toggle(
        title = "Toggle",
        illustration = R.drawable.tile_toggle,
        route = "toggle",
        content = { ToggleDemoScreen() }
    ),
    Tooltip("Tooltip"),
    UIShell("UI shell");

    companion object {
        val homeTilesDestinations = entries
            .filterNot { it == Home }
            // Show first the components that have a demo activity
            .sortedByDescending { it.route.isNotEmpty() }
    }
}
