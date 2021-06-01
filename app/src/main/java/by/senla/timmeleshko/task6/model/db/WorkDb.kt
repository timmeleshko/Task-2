package by.senla.timmeleshko.task6.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import by.senla.timmeleshko.task6.model.data.dto.WorkDto
import by.senla.timmeleshko.task6.model.interfaces.WorkDao

@Database(
    entities = [WorkDto::class],
    version = 1,
    exportSchema = false
)
abstract class WorkDb : RoomDatabase() {
    abstract fun works(): WorkDao
}