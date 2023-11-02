package dev.gabrieldrn.carboncatalog

import android.app.Application
import timber.log.Timber

class CatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
