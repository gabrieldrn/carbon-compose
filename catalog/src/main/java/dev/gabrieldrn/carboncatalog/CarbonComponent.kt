package dev.gabrieldrn.carboncatalog

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import dev.gabrieldrn.carboncatalog.buttons.ButtonsDemoActivity

enum class CarbonComponent(
    val title: String,
    @DrawableRes val illustration: Int? = null,
    val demoActivity: Class<out AppCompatActivity>? = null
) {
    Accordion("Accordion"),
    Breadcrumb("Breadcrumb"),
    Button("Button", R.drawable.tile_button, ButtonsDemoActivity::class.java),
    Checkbox("Checkbox"),
    CodeSnippet("Code snippet"),
    ContentSwitcher("Content switcher"),
    DataTable("Data table"),
    DatePicker("Date picker"),
    Dropdown("Dropdown"),
    FileUploader("File uploader"),
    Form("Form"),
    InlineLoading("Inline loading"),
    Link("Link"),
    List("List"),
    Loading("Loading"),
    Modal("Modal"),
    MultiSelect("Multi-select"),
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
    Toggle("Toggle"),
    Tooltip("Tooltip"),
    UIShell("UI shell"),
}