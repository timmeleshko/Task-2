package by.senla.timmeleshko.task6.model.dto

import androidx.room.*
import by.senla.timmeleshko.task6.utils.Converters

@Entity(tableName = "works", indices = [Index(value = ["work_id"], unique = false)])
@TypeConverters(
    Converters::class
)
data class WorkDto(
    @PrimaryKey
    @ColumnInfo(name = "work_id")
    val work_id: String,
    var media_dto: MediaDto? = null,
    var filter_dto: List<FilterDto>? = null,
    val user_id: String? = null,
    val uri_owner: String? = null,
    val media_id: String? = null,
    val date_upload: String? = null,
    val type_id: String? = null,
    val masterwork: String? = null,
    val dt_finish: String? = null,
    val sx: String? = null,
    val sy: String? = null,
    val sz: String? = null,
    val coords: CoordsDto? = null,
    val is_adult: String? = null,
    val privacy: String? = null,
    val name: String? = null,
//    val names: List<String>?,
    val description: String? = null,
    val artist_hidden_price: String? = null,
    val uri: String? = null,
    var tags: List<TagDto>? = null,
    val style_ids: List<String>? = null,
    val genre_ids: List<String>? = null,
    val counters: Counters? = null,
    val collection_id: String? = null,
    val aset_ids: List<String>? = null,
    val infos: Infos? = null,
    val flags: Flags? = null,
    val colors: ColorsDto? = null,
    val material_ids: List<String>? = null,
    val technique_ids: List<String>? = null,
    val artist_ids: List<String>? = null,
    val status: StatusDto? = null,
    val description_html: String? = null,
    val _extended: String? = null
) {

    data class Counters(
        val selections: String?,
        val comments: String?,
        val likes: String?,
        val audioguides: String?
    )

    data class Flags(
        val is_liked: String?,
        val is_can_like: String?,
        val is_can_comment: String?
    )

    data class Infos(
        val owner_name: String?,
        val collection_name: String?,
        val exposition_id: String?,
        val exposition_name: String?,
        val count_expositions: String?,
        val aset_id: String?,
        val aset_name: String?
    )

    var indexInResponse: Int = -1
}