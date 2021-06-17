package by.senla.timmeleshko.task6.model.dto

data class Data(
    val count: Int?,
    val works: List<WorkDto>?,
    val media: List<MediaDto>?,
    var filters: List<FilterDto>?,
    val techniques: List<TechniqueDto>?,
    val styles: List<StyleDto>?,
    val genres: List<GenreDto>?,
    val types: List<TypeDto>?,
    val materials: List<MaterialDto>?,
    val tags: List<TagDto>?
)
