package by.senla.timmeleshko.task6.model

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import by.senla.timmeleshko.task6.model.db.DataDb
import by.senla.timmeleshko.task6.model.api.DataApi
import by.senla.timmeleshko.task6.model.repository.WorkRepository
import by.senla.timmeleshko.task6.model.repository.DbWorkRepository

/**
 * Super simplified service locator implementation to allow us to replace default implementations
 * for testing.
 */
interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(
                            app = context.applicationContext as Application,
                            useInMemoryDb = false)
                }
                return instance!!
            }
        }

        /**
         * Allows tests to replace the default implementations.
         */
        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getRepository(type: WorkRepository.Type): WorkRepository

    fun getDataApi(): DataApi
}

/**
 * default implementation of ServiceLocator that uses production endpoints.
 */
open class DefaultServiceLocator(val app: Application, val useInMemoryDb: Boolean) : ServiceLocator {
    private val db by lazy {
        DataDb.create(app, useInMemoryDb)
    }

    private val api by lazy {
        DataApi.create()
    }

    override fun getRepository(type: WorkRepository.Type): WorkRepository {
        return DbWorkRepository(dataDb = db, dataApi = getDataApi())
    }

    override fun getDataApi(): DataApi = api
}