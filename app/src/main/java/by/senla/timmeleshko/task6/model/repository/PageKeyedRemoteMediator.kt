package by.senla.timmeleshko.task6.model.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import by.senla.timmeleshko.task6.model.Constants.INFO
import by.senla.timmeleshko.task6.model.api.DataApi
import by.senla.timmeleshko.task6.model.api.RemoteKeyDao
import by.senla.timmeleshko.task6.model.api.WorkDao
import by.senla.timmeleshko.task6.model.db.DataDb
import by.senla.timmeleshko.task6.model.dto.*
import by.senla.timmeleshko.task6.view.WorksViewModel.Companion.RECYCLER_VIEW_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val dataDb: DataDb,
    private val dataApi: DataApi,
    private val uri: String
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
                        remoteKeyDao.remoteKeys(uri)
                    }
                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextKey
                }
            }
            val data = dataApi.getData(
                offset = loadKey,
                count = RECYCLER_VIEW_PAGE_SIZE,
                uris = uri
            ).data

            val newOffset = (data.works.size + (loadKey?.toInt() ?: 0)).toString()
            val items = data.works
            data.media?.let { media -> checkMediaId(items, media) }

            dataDb.withTransaction {
                if (loadType == REFRESH) {
                    workDao.delete()
                    remoteKeyDao.delete()
                }
                remoteKeyDao.insert(RemoteKey(uri, newOffset))
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