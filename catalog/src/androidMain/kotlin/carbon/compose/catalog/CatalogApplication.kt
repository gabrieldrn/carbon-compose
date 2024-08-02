package carbon.compose.catalog

import android.app.Application
import timber.log.Timber

@Suppress("UndocumentedPublicClass")
class CatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
