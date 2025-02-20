package ru.hse.coursehse.app.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.hse.coursehse.app.core.data.dao.FavouritesDao
import ru.hse.coursehse.app.core.data.dao.TranslationHistoryDao
import ru.hse.coursehse.app.core.data.entity.Favourite
import ru.hse.coursehse.app.core.data.entity.TranslationHistory

@Database(entities = [TranslationHistory::class, Favourite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationHistoryDao(): TranslationHistoryDao

    abstract fun favouritesDao(): FavouritesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE IF NOT EXISTS favourite (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    sourceText TEXT NOT NULL,
                    translatedText TEXT NOT NULL,
                    timestamp INTEGER NOT NULL
                )
            """.trimIndent()
        )
    }
}