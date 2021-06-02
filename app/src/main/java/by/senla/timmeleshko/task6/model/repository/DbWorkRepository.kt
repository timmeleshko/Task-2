package by.senla.timmeleshko.task6.model.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import by.senla.timmeleshko.task6.model.db.DataDb
import by.senla.timmeleshko.task6.model.interfaces.DataApi
import by.senla.timmeleshko.task6.model.interfaces.WorkRepository

/**
 * Repository implementation that uses a database backed [androidx.paging.PagingSource] and
 * [androidx.paging.RemoteMediator] to load pages from network when there are no more items cached
 * in the database to load.
 */
class DbWorkRepository(val dataDb: DataDb, val dataApi: DataApi) : WorkRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun worksOfData(work_id: String, pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(dataDb, dataApi, work_id)
    ) {
        dataDb.works().worksByWorkId(work_id)
    }.flow
}