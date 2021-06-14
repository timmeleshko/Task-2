package by.senla.timmeleshko.task6.utils

import androidx.room.TypeConverter
import by.senla.timmeleshko.task6.model.dto.*
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun toStatusDto(value: StatusDto?): String = Gson().toJson(value)

    @TypeConverter
    fun fromStatusDto(value: String): StatusDto? = Gson().fromJson(value, StatusDto::class.java)

    @TypeConverter
    fun toMediaDto(value: MediaDto?): String = Gson().toJson(value)

    @TypeConverter
    fun fromMediaDto(value: String): MediaDto? = Gson().fromJson(value, MediaDto::class.java)

    @TypeConverter
    fun toCoordsDto(value: CoordsDto?): String = Gson().toJson(value)

    @TypeConverter
    fun fromCoordsDto(value: String): CoordsDto? = Gson().fromJson(value, CoordsDto::class.java)

    @TypeConverter
    fun toColorsDto(value: ColorsDto?): String = Gson().toJson(value)

    @TypeConverter
    fun fromColorsDto(value: String): ColorsDto = Gson().fromJson(value, ColorsDto::class.java)

    @TypeConverter
    fun toCounters(value: WorkDto.Counters?): String = Gson().toJson(value)

    @TypeConverter
    fun fromCounters(value: String): WorkDto.Counters? = Gson().fromJson(value, WorkDto.Counters::class.java)

    @TypeConverter
    fun toFlags(value: WorkDto.Flags?): String = Gson().toJson(value)

    @TypeConverter
    fun fromFlags(value: String): WorkDto.Flags? = Gson().fromJson(value, WorkDto.Flags::class.java)

    @TypeConverter
    fun toInfos(value: WorkDto.Infos?): String = Gson().toJson(value)

    @TypeConverter
    fun fromInfos(value: String): WorkDto.Infos? = Gson().fromJson(value, WorkDto.Infos::class.java)

    @TypeConverter
    fun toList(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun fromList(value: String) = Gson().fromJson(value, Array<String>::class.java)?.toList()

    @TypeConverter
    fun toTagList(value: List<TagDto>?): String = Gson().toJson(value)

    @TypeConverter
    fun fromTagList(value: String) = Gson().fromJson(value, Array<TagDto>::class.java)?.toList()

    @TypeConverter
    fun toFilterList(value: List<FilterDto>?): String = Gson().toJson(value)

    @TypeConverter
    fun fromFilterList(value: String) = Gson().fromJson(value, Array<FilterDto>::class.java)?.toList()
}