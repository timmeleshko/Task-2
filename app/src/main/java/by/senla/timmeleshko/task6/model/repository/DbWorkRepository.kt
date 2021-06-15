package by.senla.timmeleshko.task6.model.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import by.senla.timmeleshko.task6.model.db.DataDb
import by.senla.timmeleshko.task6.model.api.DataApi

class DbWorkRepository(val dataDb: DataDb, val dataApi: DataApi) : WorkRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun worksOfData(uri: String, pageSize: Int) = Pager(
        config = PagingConfig(pageSize = pageSize),
        remoteMediator = PageKeyedRemoteMediator(dataDb, dataApi, uri)
    ) {
        dataDb.works().works()
    }.flow
}