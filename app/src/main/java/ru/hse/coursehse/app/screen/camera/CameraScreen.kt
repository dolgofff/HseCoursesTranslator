package ru.hse.coursehse.app.screen.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import ru.hse.coursehse.app.screen.translation.TranslationViewModel
import java.io.File

@Composable
fun CameraScreen(
    translationViewModel: TranslationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val recognizeTextState = remember { mutableStateOf<String>("") }

    val uiState = translationViewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            cameraProviderFuture = cameraProvider,
            lifecycleOwner = lifecycleOwner,
            imageCapture = imageCapture,
            onClick = {
                val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
                    createTempFile()
                ).build()

                imageCapture.takePicture(
                    outputFileOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val uri = outputFileResults.savedUri
                            uri?.let {
                                val bitmap = BitmapFactory.decodeFile(it.path)
                                imageBitmap.value = bitmap

                                recognizeTextFromImage(
                                    bitmap = bitmap,
                                    recognizeTextState = recognizeTextState,
                                    viewModel = translationViewModel
                                )
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {}
                    }
                )
            },
        )

        Text(
            text = recognizeTextState.value,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }

}

@Composable
fun CameraPreview(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner,
    imageCapture: ImageCapture,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.surfaceProvider = previewView.surfaceProvider
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = { onClick() }),

        )
}

private fun createTempFile(): File {
    return File.createTempFile("cameraImg_", System.nanoTime().toString())
}

private fun recognizeTextFromImage(
    bitmap: Bitmap,
    recognizeTextState: MutableState<String>,
    viewModel: TranslationViewModel
) {
    val inputImage = InputImage.fromBitmap(bitmap, 0)

    val textRecognizer: TextRecognizer = TextRecognition.getClient()

    textRecognizer.process(inputImage).addOnSuccessListener { result ->
        val recognizedText = result.textBlocks.flatMap { block ->
            block.lines.map { it.text }
        }

        viewModel.updateInputText(recognizedText.toString())
        viewModel.translateText()

        recognizeTextState.value = viewModel.uiState.value.translatedText.orEmpty()
    }.addOnFailureListener {
        recognizeTextState.value = "Text recognition failed!"
    }
}

data class RecognitionLine(
    val text: String,
)

fun Text.Line.toRecognitionLine(): RecognitionLine {
    return RecognitionLine(
        text = text
    )
}