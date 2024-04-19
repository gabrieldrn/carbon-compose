package carbon.compose.catalog.home

import androidx.annotation.DrawableRes
import androidx.navigation.NavDestination
import carbon.compose.catalog.R

@Suppress("UndocumentedPublicClass", "UndocumentedPublicProperty")
enum class Destination(
    val title: String,
    @DrawableRes val illustration: Int? = null,
    val route: String = ""
) {
    Home("Carbon Design System", null, "home"),
    Accordion("Accordion"),
    Breadcrumb("Breadcrumb"),
    Button("Button", R.drawable.tile_button, "button"),
    Checkbox("Checkbox", R.drawable.tile_checkbox, "checkbox"),
    CodeSnippet("Code snippet"),
    ContentSwitcher("Content switcher"),
    DataTable("Data table"),
    DatePicker("Date picker"),
    Dropdown_SubDestination("Dropdown", R.drawable.tile_dropdown, "dropdown"),
    Dropdown_Home("Dropdown", null, "dropdown_home"),
    Dropdown_Default("Dropdown", null, "dropdown/default"),
    Dropdown_MultiSelect("Multi-select", null, "dropdown/multiselect"),
    FileUploader("File uploader"),
    Form("Form"),
    InlineLoading("Inline loading"),
    Link("Link"),
    List("List"),
    Loading("Loading"),
    Modal("Modal"),
    MultiSelect("Multi-select", R.drawable.tile_mutliselect, Dropdown_MultiSelect.route),
    Notification("Notification"),
    NumberInput("Number input"),
    Pagination("Pagination"),
    ProgressIndicator("Progress indicator"),
    OverflowMenu("Overflow menu"),
    RadioButton("Radio button"),
    Search("Search"),
    Select("Select"),
    Slider("Slider"),
    StructuredList("Structured list"),
    Tabs("Tabs"),
    Tag("Tag"),
    TextInput("Text input"),
    Tile("Tile"),
    Toggle("Toggle", R.drawable.tile_toggle, "toggle"),
    Tooltip("Tooltip"),
    UIShell("UI shell");

    companion object {
        val homeTilesDestinations = entries
            .filterNot {
                it in arrayOf(Home, Dropdown_Home, Dropdown_Default, Dropdown_MultiSelect)
            }
            // Show first the components that have a demo activity
            .sortedByDescending { it.route.isNotEmpty() }

        infix fun NavDestination.eq(destination: Destination) = destination.route == route
        infix fun Destination.eq(destination: NavDestination) = route == destination.route
    }
}
