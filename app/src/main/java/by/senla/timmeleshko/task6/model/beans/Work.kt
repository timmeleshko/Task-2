package by.senla.timmeleshko.task6.model.beans

import java.io.Serializable

data class Work(
    val work_id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val counters: Counter? = null
) : Serializable
