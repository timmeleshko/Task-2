package by.senla.timmeleshko.task6.model.beans

data class MediaDto(
    val media_id: String?,
    val type: String?,
    val user_id: String?,
    val caption: String?,
    val data: Data?,
    val create_date: String?,
    val use_type: String?,
    val uri: String?,
    val base_url: String?,
    val _extended: String?
) {
    data class Data(
        val version: String?,
        val version_big: String?,
        val version_orig: String?,
        val sizes: Size?,
        val x: String?,
        val y: String?,
        val ratio: String?,
        val ext: String?,
        val is_animated: String?
    )

    data class Size(
            val orig: SizeOrig?
    )

    data class SizeOrig(
            val x: String?,
            val y: String?,
            val hash_file_name: String?
    )
}