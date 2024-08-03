package carbon.compose.catalog.dropdown

import carbon.compose.catalog.BaseDestination
import carbon.compose.catalog.Destination
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
