package by.senla.timmeleshko.task6.model.repository

import by.senla.timmeleshko.task6.model.data.Listing
import by.senla.timmeleshko.task6.model.data.dto.WorkDto

interface WorkRepository {
    fun getWorks(id: Int, pageSize: Int): Listing<WorkDto>
}