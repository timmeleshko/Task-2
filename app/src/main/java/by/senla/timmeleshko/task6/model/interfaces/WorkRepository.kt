package by.senla.timmeleshko.task6.model.interfaces

import androidx.paging.PagingData
import by.senla.timmeleshko.task6.model.beans.WorkDto
import kotlinx.coroutines.flow.Flow

interface WorkRepository {

    fun worksOfData(work_id: String, pageSize: Int): Flow<PagingData<WorkDto>>

    enum class Type {
        DB
    }
}