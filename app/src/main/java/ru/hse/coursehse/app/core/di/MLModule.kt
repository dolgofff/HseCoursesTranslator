package ru.hse.coursehse.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.hse.coursehse.app.core.domain.camera.GetTextFromImageUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MLModule {

    @Provides
    @Singleton
    fun provideTextRecognition(): GetTextFromImageUseCase {
        return GetTextFromImageUseCase()
    }
}