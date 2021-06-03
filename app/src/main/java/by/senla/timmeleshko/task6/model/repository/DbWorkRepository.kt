package by.senla.timmeleshko.task6.model.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import by.senla.timmeleshko.task6.model.db.DataDb
import by.senla.timmeleshko.task6.model.interfaces.DataApi
import by.senla.timmeleshko.task6.model.interfaces.WorkRepository

class DbWorkRepository(val dataDb: DataDb, val dataApi: DataApi) : WorkRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun worksOfData(work_id: String, pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(dataDb, dataApi, work_id)
    ) {
        dataDb.works().works()
    }.flow
}