package by.senla.timmeleshko.task6

import by.senla.timmeleshko.task6.model.api.DataApi
import by.senla.timmeleshko.task6.model.dto.DataWrapper
import by.senla.timmeleshko.task6.model.dto.WorkDto
import by.senla.timmeleshko.task6.model.dto.WorkResponse
import retrofit2.http.Query
import java.io.IOException
import java.lang.Integer.min

class FakeDataApi : DataApi {

    private val model = mutableMapOf<WorkDto, Work>()
    var failureMsg: String? = null

    fun addWork(workDto: WorkDto) {
        val work = model.getOrPut(workDto) {
            Work(items = arrayListOf())
        }
        work.items.add(workDto)
    }

    override suspend fun getDataForFilterAsync() : DataWrapper {
        return DataWrapper(null)
    }

    override suspend fun getData(
        @Query("offset") offset: String?,
        @Query("count") count: Int,
        @Query("uris") uris: String
    ) : DataApi.ListingResponse {
        failureMsg?.let {
            throw IOException(it)
        }
        val items = findPosts(offset, count)
        return DataApi.ListingResponse(
            DataApi.ListingData(
                works = items.map { it.data },
                media = null
            )
        )
    }

    private fun findPosts(
        offset: String?,
        count: Int
    ): List<WorkResponse> {
        if (offset == null) return emptyList()
        val work = Work()
        val works = work.findWorks(count)
        return works.map { WorkResponse(it.copy()) }
    }

    private class Work(val items: MutableList<WorkDto> = arrayListOf()) {
        fun findWorks(count: Int): List<WorkDto> {
            val index = items.indexOfFirst { false }
            if (index == -1) {
                return emptyList()
            }
            val startPos = index + 1
            return items.subList(startPos, min(items.size, startPos + count))
        }
    }
}