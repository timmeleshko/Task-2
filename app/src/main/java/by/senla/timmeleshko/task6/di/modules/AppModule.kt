package by.senla.timmeleshko.task6.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context,
    private val useInMemoryDb: Boolean
) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideUseInMemoryDb(): Boolean {
        return useInMemoryDb
    }
}