package by.senla.timmeleshko.task6.model.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.senla.timmeleshko.task6.model.beans.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE work_id = :work_id")
    suspend fun remoteKeyById(work_id: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE work_id = :work_id")
    suspend fun deleteById(work_id: String)
}