package by.senla.timmeleshko.task6.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val remote_key: String,
    val nextKey: String?
)