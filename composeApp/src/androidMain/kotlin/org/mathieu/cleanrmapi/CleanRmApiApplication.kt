package org.mathieu.cleanrmapi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.mathieu.cleanrmapi.audio.platformModule

class CleanRmApiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CleanRmApiApplication)
            // On injecte la dépendance SoundPlayer spécifique à la plateforme
            modules(platformModule(this@CleanRmApiApplication))
        }
    }
}
