package com.csiq6823.zenlife.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// TODO: CHAPTER 12 - Mark this interface structure as a Room Data Access Object (DAO)
@Dao
interface WellnessDao {

    // TODO: CHAPTER 12 & 13 - Write a query selecting all records ordered by timestamp descending.
    // Ensure that it outputs an asynchronous, continuous reactive Kotlin Flow pipeline stream.
    @Query("SELECT * FROM wellness_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<WellnessLog>>

    // TODO: CHAPTER 12 & 13 - Define an asynchronous suspend method to insert a single log item.
    @Insert
    suspend fun insertLog(log: WellnessLog)


    // TODO: CHAPTER 12 & 13 - Define an asynchronous suspend method to wipe all records from the table.
    @Query("DELETE FROM wellness_logs")
    suspend fun clearAllLogs()
}
