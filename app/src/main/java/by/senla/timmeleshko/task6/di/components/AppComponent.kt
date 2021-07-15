package by.senla.timmeleshko.task6.di.components

import by.senla.timmeleshko.task6.di.modules.AppModule
import by.senla.timmeleshko.task6.di.modules.DbModule
import by.senla.timmeleshko.task6.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DbModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
}