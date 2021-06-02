package by.senla.timmeleshko.task6.model.repository

import androidx.paging.PagingData
import by.senla.timmeleshko.task6.model.beans.WorkDto
import kotlinx.coroutines.flow.Flow

/**
 * Common interface shared by the different repository implementations.
 * Note: this only exists for sample purposes - typically an app would implement a repo once, either
 * network+db, or network-only
 */
interface WorkRepository {

    fun worksOfData(work_id: String, pageSize: Int): Flow<PagingData<WorkDto>>

    enum class Type {
        IN_MEMORY_BY_ITEM,
        DB
    }
}