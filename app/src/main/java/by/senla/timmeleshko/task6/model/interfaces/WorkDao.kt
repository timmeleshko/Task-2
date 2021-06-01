package by.senla.timmeleshko.task6.model.interfaces

import androidx.paging.DataSource
import androidx.room.*
import by.senla.timmeleshko.task6.model.data.dto.WorkDto

@Dao
interface WorkDao {
    @Insert
    fun insert(works : List<WorkDto>)

    @Query("SELECT * FROM works WHERE work_id IN (:id)")
    fun loadAllByIds(id: IntArray): DataSource.Factory<Int, WorkDto>

    @Query("SELECT * FROM works")
    fun getAll(): DataSource.Factory<Int, WorkDto>

    @Query("DELETE FROM works WHERE id = :id")
    fun deleteById(id: Int)

    @Delete
    fun delete(work: WorkDto)

    @Query("SELECT MAX(indexInResponse) + 1 FROM works WHERE id = :id")
    fun getNextIndex(id: Int) : Int
}