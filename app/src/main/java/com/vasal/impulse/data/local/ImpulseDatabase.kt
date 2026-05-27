package com.vasal.impulse.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [DailyProgressEntity::class, QuestEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ImpulseDatabase : RoomDatabase() {
    abstract fun dailyProgressDao(): DailyProgressDao
    abstract fun questDao(): QuestDao

    companion object {
        @Volatile
        private var instance: ImpulseDatabase? = null

        private val Migration1To2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS quests (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        title TEXT NOT NULL,
                        description TEXT NOT NULL,
                        category TEXT NOT NULL,
                        durationMinutes INTEGER NOT NULL,
                        difficulty INTEGER NOT NULL,
                        importance INTEGER NOT NULL,
                        energyCost INTEGER NOT NULL,
                        points INTEGER NOT NULL,
                        kind TEXT NOT NULL,
                        createdAt INTEGER NOT NULL,
                        updatedAt INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        fun getInstance(context: Context): ImpulseDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ImpulseDatabase::class.java,
                    "impulse.db"
                )
                    .addMigrations(Migration1To2)
                    .build()
                    .also { instance = it }
            }
    }
}
