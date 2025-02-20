package ru.hse.coursehse.app.screen.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.hse.coursehse.app.core.data.entity.TranslationHistory
import ru.hse.coursehse.app.core.data.dao.TranslationHistoryDao
import javax.inject.Inject


// Можно использовать usecase, но можно inject dao
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val translationHistoryDao: TranslationHistoryDao,
) : ViewModel() {
    fun getHistory(): Flow<List<TranslationHistory>> {
        return translationHistoryDao.getTranslationHistory()
    }
}