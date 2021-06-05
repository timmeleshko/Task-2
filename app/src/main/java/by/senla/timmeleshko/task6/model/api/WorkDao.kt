package by.senla.timmeleshko.task6.model.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.senla.timmeleshko.task6.model.dto.WorkDto

@Dao
interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(works: List<WorkDto>)

    @Query("SELECT * FROM works")
    fun works(): PagingSource<Int, WorkDto>

    @Query("DELETE FROM works")
    suspend fun delete()
}