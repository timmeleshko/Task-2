package by.senla.timmeleshko.task6.model.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.senla.timmeleshko.task6.model.dto.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE remote_key = :remote_key")
    suspend fun remoteKeys(remote_key: String): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun delete()
}