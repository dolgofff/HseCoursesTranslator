package ru.hse.coursehse.app.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.hse.coursehse.app.core.data.entity.Favourite
import ru.hse.coursehse.app.core.data.entity.TranslationHistory

@Dao
interface FavouritesDao {
    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Query("SELECT * FROM favourite ORDER BY timestamp")
    fun getFavourites(): Flow<List<Favourite>>
}