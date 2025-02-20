package ru.hse.coursehse.app.screen.translation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hse.coursehse.app.ui.LanguageSelector
import ru.hse.coursehse.app.ui.TextInput
import ru.hse.coursehse.app.ui.TranslateButton
import ru.hse.coursehse.app.ui.TranslationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TranslationScreen(
    viewModel: TranslationViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(title = { Text("Translation App") })

        LanguageSelector(
            sourceLanguage = uiState.value.sourceLang,
            targetLanguage = uiState.value.targetLang,
            onSwapLanguages = { viewModel.swapLanguages() },
            onSelectSourceLanguage = { viewModel.selectSourceLanguage(it) },
            onSelectTargetLanguage = { viewModel.selectTargetLanguage(it) }
        )
        TextInput(
            language = uiState.value.sourceLang.title,
            text = uiState.value.inputText,
            onTextChange = { viewModel.updateInputText(it) },
            onClearText = { viewModel.clearInputText() },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TranslateButton(
            onTranslate = { viewModel.translateText() },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        uiState.value.translatedText?.let {
            TranslationResult(
                result = it,
                modifier = Modifier.padding(horizontal = 16.dp),
                addToFavourite = {
                    viewModel.addToFavorite()
                }
            )
        }
    }
}