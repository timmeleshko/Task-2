package by.senla.timmeleshko.task6.model.dto

data class StyleDto(
    val style_id: String?,
    val parent_id: String?,
    val media_id: String?,
    override val name: String?,
    val description: String?,
    override val uri: String?,
    val _extended: String?,
    val cnt_works: String?
) : GeneralData