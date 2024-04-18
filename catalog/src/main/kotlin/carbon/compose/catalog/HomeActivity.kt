package carbon.compose.catalog

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import carbon.compose.catalog.theme.CarbonCatalogTheme

@Suppress("UndocumentedPublicClass")
class HomeActivity : ComponentActivity() {

    private val components: List<CarbonComponent> = CarbonComponent
        .entries
        // Show first the components that have a demo activity
        .sortedByDescending { it.demoActivity != null }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContent {
            CarbonCatalogTheme {
                HomeScreen(
                    components = components,
                    onTileClicked = { component ->
                        component.demoActivity?.let {
                            startActivity(Intent(this@HomeActivity, it))
                        }
                    }
                )
            }
        }
    }
}
