package ru.hse.coursehse.app.screen.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.coursehse.app.core.domain.camera.GetTextFromImageUseCase
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val getTextFromImageUseCase: GetTextFromImageUseCase,
   // private val translationUseCase: GetTextFromImageUseCase,
) : ViewModel() {

    fun getTextFromImage(bitmap: Bitmap): String {
        return getTextFromImageUseCase.recognizeTextFromImage(bitmap)
    }
}