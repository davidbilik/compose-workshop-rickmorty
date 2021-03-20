package cz.ackee.ackeecompose

import android.app.Application
import cz.ackee.ackeecompose.di.characterModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
        Timber.plant(Timber.DebugTree())
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@App)
            modules(characterModule)
        }
    }
}