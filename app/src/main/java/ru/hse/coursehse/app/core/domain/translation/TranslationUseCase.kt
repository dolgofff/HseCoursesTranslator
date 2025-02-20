package ru.hse.coursehse.app.core.domain.translation

import ru.hse.coursehse.app.core.TranslationApi
import ru.hse.coursehse.app.core.TranslationApi.TranslationResponse
import ru.hse.coursehse.app.core.domain.LanguageCode
import javax.inject.Inject

class TranslationUseCase @Inject constructor(
    private val translationApi: TranslationApi
) {
    suspend fun translate(sl: LanguageCode, dl: LanguageCode, text: String): TranslationResponse {
        return translationApi.translate(sl.code, dl.code, text)
    }
}