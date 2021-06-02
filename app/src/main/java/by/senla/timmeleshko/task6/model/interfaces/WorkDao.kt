package by.senla.timmeleshko.task6.model.interfaces

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.senla.timmeleshko.task6.model.beans.WorkDto

@Dao
interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(works: List<WorkDto>)

    @Query("SELECT * FROM works WHERE work_id = :work_id ORDER BY indexInResponse ASC")
    fun worksByWorkId(work_id: String): PagingSource<Int, WorkDto>

    @Query("DELETE FROM works WHERE work_id = :work_id")
    suspend fun deleteByWorkId(work_id: String)

    @Query("SELECT MAX(indexInResponse) + 1 FROM works WHERE work_id = :work_id")
    suspend fun getNextIndexInWorkId(work_id: String): Int
}