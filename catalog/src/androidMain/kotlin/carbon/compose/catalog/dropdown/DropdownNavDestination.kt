package carbon.compose.catalog.dropdown

import androidx.annotation.DrawableRes
import carbon.compose.catalog.BaseDestination
import carbon.compose.catalog.Destination

@Suppress("UndocumentedPublicClass", "UndocumentedPublicProperty")
enum class DropdownNavDestination(
    override val title: String,
    @DrawableRes val illustration: Int? = null,
    override val route: String = "",
) : BaseDestination {
    Home("Dropdown", null, "dropdown_home"),
    Default("Dropdown", null, "dropdown/default"),
    MultiSelect("Multi-select", null, Destination.MultiSelect.route),
}
