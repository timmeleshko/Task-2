package by.senla.timmeleshko.task6.model.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import by.senla.timmeleshko.task6.model.Constants
import by.senla.timmeleshko.task6.model.beans.MediaDto
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.model.beans.WorkIdRemoteKey
import by.senla.timmeleshko.task6.model.db.DataDb
import by.senla.timmeleshko.task6.model.interfaces.DataApi
import by.senla.timmeleshko.task6.model.interfaces.WorkDao
import by.senla.timmeleshko.task6.model.interfaces.WorkIdRemoteKeyDao
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val dataDb: DataDb,
    private val dataApi: DataApi,
    private val workId: String
) : RemoteMediator<Int, WorkDto>() {

    private val workDao: WorkDao = dataDb.works()
    private val remoteKeyDao: WorkIdRemoteKeyDao = dataDb.remoteKeys()

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
                        remoteKeyDao.remoteKeyByWork(workId)
                    }
                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPageKey
                }
            }
            Log.i(Constants.INFO, loadKey.toString())
            val data = dataApi.getData(
                offset = loadKey,
                count = when (loadType) {
                    REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            ).data

            val items = data.works.map { it }
            checkMediaId(items, data.media.map { it })

            dataDb.withTransaction {
                if (loadType == REFRESH) {
                    workDao.deleteByWorkId(workId)
                    remoteKeyDao.deleteByWorkId(workId)
                }
                remoteKeyDao.insert(WorkIdRemoteKey(workId, ))
                workDao.insertAll(items)
            }
            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}