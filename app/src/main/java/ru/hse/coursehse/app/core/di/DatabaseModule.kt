package ru.hse.coursehse.app.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hse.coursehse.app.core.data.AppDatabase
import ru.hse.coursehse.app.core.data.dao.FavouritesDao
import ru.hse.coursehse.app.core.data.dao.TranslationHistoryDao
import ru.hse.coursehse.app.core.data.entity.Favourite
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideHistoryDao(db: AppDatabase) : TranslationHistoryDao {
        return db.translationHistoryDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(db: AppDatabase) : FavouritesDao {
        return db.favouritesDao()
    }
}