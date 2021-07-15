package by.senla.timmeleshko.task6.di.modules

import android.content.Context
import by.senla.timmeleshko.task6.model.api.DataApi
import by.senla.timmeleshko.task6.model.db.DataDb
import by.senla.timmeleshko.task6.model.repository.DbWorkRepository
import by.senla.timmeleshko.task6.model.repository.WorkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDataApi(): DataApi {
        return DataApi.create()
    }

    @Provides
    @Singleton
    fun provideDataDb(context: Context, useInMemoryDb: Boolean): DataDb {
        return DataDb.create(context, useInMemoryDb)
    }

    @Provides
    @Singleton
    fun provideRepository(dataDb: DataDb, dataApi: DataApi): WorkRepository {
        return DbWorkRepository(dataDb, dataApi)
    }
}