package by.senla.timmeleshko.task6.model.dto

data class GenreDto(
    val genre_id: String?,
    val media_id: String?,
    override val name: String?,
    val description: String?,
    override val uri: String?,
    val _extended: String?,
    val cnt_works: String?
) : GeneralData