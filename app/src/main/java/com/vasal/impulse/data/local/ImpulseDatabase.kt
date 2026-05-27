package com.vasal.impulse.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DailyProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImpulseDatabase : RoomDatabase() {
    abstract fun dailyProgressDao(): DailyProgressDao

    companion object {
        @Volatile
        private var instance: ImpulseDatabase? = null

        fun getInstance(context: Context): ImpulseDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ImpulseDatabase::class.java,
                    "impulse.db"
                ).build().also { instance = it }
            }
    }
}
