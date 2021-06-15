package by.senla.timmeleshko.task6.model.repository

import androidx.paging.PagingData
import by.senla.timmeleshko.task6.model.dto.WorkDto
import kotlinx.coroutines.flow.Flow

interface WorkRepository {

    fun worksOfData(uri: String, pageSize: Int): Flow<PagingData<WorkDto>>

    enum class Type {
        DB
    }
}