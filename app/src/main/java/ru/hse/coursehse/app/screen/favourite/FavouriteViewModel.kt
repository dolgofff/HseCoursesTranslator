package ru.hse.coursehse.app.screen.favourite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.hse.coursehse.app.core.data.dao.FavouritesDao
import ru.hse.coursehse.app.core.data.entity.Favourite
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouritesDao: FavouritesDao,
) : ViewModel() {
    fun getFavourites(): Flow<List<Favourite>> {
        return favouritesDao.getFavourites()
    }
}