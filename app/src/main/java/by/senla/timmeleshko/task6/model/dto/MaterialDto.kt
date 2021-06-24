package by.senla.timmeleshko.task6.model.dto

data class MaterialDto(
    val material_id: String?,
    val media_id: String?,
    override val name: String?,
    override val uri: String?,
    val _extended: String?
) : GeneralData