package com.vasal.impulse.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [DailyProgressEntity::class, QuestEntity::class],
    version = 4,
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

        private val Migration2To3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE daily_progress ADD COLUMN dailyTaskCompleted INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE daily_progress ADD COLUMN traceTitle TEXT")
            }
        }

        private val Migration3To4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE daily_progress ADD COLUMN completedExtraQuestIds TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE daily_progress ADD COLUMN completedExtraQuestTitles TEXT NOT NULL DEFAULT ''")
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
                    .addMigrations(Migration2To3)
                    .addMigrations(Migration3To4)
                    .build()
                    .also { instance = it }
            }
    }
}
