package by.senla.timmeleshko.task6.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.model.beans.WorkIdRemoteKey
import by.senla.timmeleshko.task6.model.interfaces.WorkDao
import by.senla.timmeleshko.task6.model.interfaces.WorkIdRemoteKeyDao

@Database(
    entities = [WorkDto::class, WorkIdRemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class DataDb : RoomDatabase() {
    companion object {
        fun create(context: Context, useInMemory: Boolean): DataDb {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, DataDb::class.java)
            } else {
                Room.databaseBuilder(context, DataDb::class.java, "data-works.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun works(): WorkDao
    abstract fun remoteKeys(): WorkIdRemoteKeyDao
}