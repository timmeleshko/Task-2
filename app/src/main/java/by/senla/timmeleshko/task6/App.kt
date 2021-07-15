package by.senla.timmeleshko.task6

import android.app.Application
import by.senla.timmeleshko.task6.di.components.AppComponent
import by.senla.timmeleshko.task6.di.components.DaggerAppComponent
import by.senla.timmeleshko.task6.di.modules.AppModule

class App: Application() {

    companion object {
        const val USE_IN_MEMORY_DB = false
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this, USE_IN_MEMORY_DB))
            .build()
    }

    fun appComponent(): AppComponent {
        return appComponent
    }
}