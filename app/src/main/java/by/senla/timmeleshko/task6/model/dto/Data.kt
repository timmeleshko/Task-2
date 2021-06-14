package by.senla.timmeleshko.task6.model.dto

data class Data(
    val count: Int?,
    val works: List<WorkDto>?,
    val media: List<MediaDto>?,
    val filters: List<FilterDto>?
)