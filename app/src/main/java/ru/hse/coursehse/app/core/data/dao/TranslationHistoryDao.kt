package ru.hse.coursehse.app.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.hse.coursehse.app.core.data.entity.TranslationHistory


@Dao
interface TranslationHistoryDao {
    @Insert
    suspend fun insertHistory(history: TranslationHistory)

    //А причём тут flow, как это работает?

    @Query("SELECT * FROM translation_history ORDER BY timestamp")
    fun getTranslationHistory(): Flow<List<TranslationHistory>>
}