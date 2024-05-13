package carbon.catalog.home

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import carbon.catalog.R
import carbon.catalog.buttons.ButtonDemoScreen
import carbon.catalog.checkbox.CheckboxDemoScreen
import carbon.catalog.dropdown.BaseDestination
import carbon.catalog.dropdown.MultiselectDropdownScreen
import carbon.catalog.radiobutton.LoadingDemoScreen
import carbon.catalog.radiobutton.RadioButtonDemoScreen
import carbon.catalog.toggle.ToggleDemoScreen

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
        content = { MultiselectDropdownScreen() }
    ),
    Notification("Notification"),
    NumberInput("Number input"),
    Pagination("Pagination"),
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
    TextInput("Text input"),
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
