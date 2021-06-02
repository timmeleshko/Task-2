package by.senla.timmeleshko.task6.model.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.senla.timmeleshko.task6.model.beans.WorkIdRemoteKey

@Dao
interface WorkIdRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: WorkIdRemoteKey)

    @Query("SELECT * FROM remote_keys WHERE work_id = :work_id")
    suspend fun remoteKeyByWork(work_id: String): WorkIdRemoteKey

    @Query("DELETE FROM remote_keys WHERE work_id = :work_id")
    suspend fun deleteByWorkId(work_id: String)
}