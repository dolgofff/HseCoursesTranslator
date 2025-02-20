package ru.hse.coursehse.app.core.domain.favourite

import ru.hse.coursehse.app.core.data.dao.FavouritesDao
import ru.hse.coursehse.app.core.data.entity.Favourite
import javax.inject.Inject

class SaveFavouriteUseCase @Inject constructor(
    private val favouriteDao: FavouritesDao
) {
    suspend fun save(sourceText: String, translatedText: String) {
        favouriteDao.insertFavourite(
            Favourite(
                sourceText = sourceText, translatedText = translatedText
            )
        )
    }
}