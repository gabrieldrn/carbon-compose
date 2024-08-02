package carbon.compose.catalog.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import carbon.compose.catalog.Catalog

@Suppress("UndocumentedPublicClass")
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContent {
            Catalog(
                onOpenLink = {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    )
                }
            )
        }
    }
}
