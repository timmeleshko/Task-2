package by.senla.timmeleshko.task6.model.beans

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
    var media_dto: MediaDto?,
    val id: Int?,
    val user_id: String?,
    val uri_owner: String?,
    val media_id: String?,
    val date_upload: String?,
    val type_id: String?,
    val masterwork: String?,
    val dt_finish: String?,
    val sx: String?,
    val sy: String?,
    val sz: String?,
    val coords: CoordsDto?,
    val is_adult: String?,
    val privacy: String?,
    val name: String?,
    val names: List<String>?,
    val description: String?,
    val artist_hidden_price: String?,
    val uri: String?,
    var tags: List<TagDto>?,
    val style_ids: List<String>?,
    val genre_ids: List<String>?,
    val counters: Counters?,
    val collection_id: String?,
    val aset_ids: List<String>?,
    val infos: Infos?,
    val flags: Flags?,
    val colors: ColorsDto?,
    val material_ids: List<String>?,
    val technique_ids: List<String>?,
    val artist_ids: List<String>?,
    val status: StatusDto?,
    val description_html: String?,
    val _extended: String?
) {

    var indexInResponse: Int = -1

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
}