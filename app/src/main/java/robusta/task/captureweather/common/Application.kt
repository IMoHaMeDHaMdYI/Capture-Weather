package robusta.task.captureweather.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import robusta.task.captureweather.di.appModule

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            fileProperties()
            modules(appModule)
        }
    }
}