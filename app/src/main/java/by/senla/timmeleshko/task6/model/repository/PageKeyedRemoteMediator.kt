package by.senla.timmeleshko.task6.model.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import by.senla.timmeleshko.task6.model.Constants.INFO
import by.senla.timmeleshko.task6.model.beans.MediaDto
import by.senla.timmeleshko.task6.model.beans.RemoteKey
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.model.db.DataDb
import by.senla.timmeleshko.task6.model.interfaces.DataApi
import by.senla.timmeleshko.task6.model.interfaces.RemoteKeyDao
import by.senla.timmeleshko.task6.model.interfaces.WorkDao
import by.senla.timmeleshko.task6.model.network.WorksViewModel.Companion.RECYCLER_VIEW_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val dataDb: DataDb,
    private val dataApi: DataApi,
    private val work_id: String
) : RemoteMediator<Int, WorkDto>() {

    private val workDao: WorkDao = dataDb.works()
    private val remoteKeyDao: RemoteKeyDao = dataDb.remoteKey()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private fun checkMediaId(worksList: List<WorkDto>, mediaList: List<MediaDto>) : List<WorkDto> {
        mediaList.forEach { a -> worksList.stream()
            .filter { b -> b.media_id == a.media_id }
            .forEach { c -> c.media_dto = a }
        }
        return worksList
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WorkDto>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                REFRESH -> null
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val remoteKey = dataDb.withTransaction {
                        remoteKeyDao.remoteKeyById(work_id)
                    }
                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextKey
                }
            }
            val data = dataApi.getData(
                offset = loadKey,
                count = when (loadType) {
                    REFRESH -> state.config.initialLoadSize
                    else -> RECYCLER_VIEW_PAGE_SIZE
                }
            ).data

            val items = data.works.map { it }
            data.media?.let { media -> checkMediaId(items, media.map { it }) }

            val newOffset = (data.works.size + (loadKey?.toInt() ?: 0)).toString()
            dataDb.withTransaction {
                if (loadType == REFRESH) {
                    workDao.deleteById(work_id)
                    remoteKeyDao.deleteById(work_id)
                }
                remoteKeyDao.insert(RemoteKey(work_id, newOffset))
                workDao.insertAll(items)
            }
            Log.i(INFO, newOffset)
            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}