package ru.hse.coursehse.app.core.domain.history

import ru.hse.coursehse.app.core.data.entity.TranslationHistory
import ru.hse.coursehse.app.core.data.dao.TranslationHistoryDao
import javax.inject.Inject

class SaveHistoryUseCase @Inject constructor(
    private val translationHistoryDao: TranslationHistoryDao
) {
    suspend fun save(sourceText: String, translatedText: String) {
        translationHistoryDao.insertHistory(
            TranslationHistory(
                sourceText = sourceText, translatedText = translatedText
            )
        )
    }
}