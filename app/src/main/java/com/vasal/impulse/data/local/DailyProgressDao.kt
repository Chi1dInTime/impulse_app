package com.vasal.impulse.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyProgressDao {
    @Query("SELECT * FROM daily_progress WHERE date = :date LIMIT 1")
    fun observeByDate(date: String): Flow<DailyProgressEntity?>

    @Query("SELECT * FROM daily_progress WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): DailyProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: DailyProgressEntity)
}
