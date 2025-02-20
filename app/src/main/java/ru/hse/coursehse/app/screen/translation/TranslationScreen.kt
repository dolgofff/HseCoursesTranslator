package ru.hse.coursehse.app.screen.translation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Translation App") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LanguageSelector(
                sourceLanguage = uiState.value.sourceLang,
                targetLanguage = uiState.value.targetLang,
                onSwapLanguages = { viewModel.swapLanguages() },
                onSelectSourceLanguage = { viewModel.selectSourceLanguage(it) },
                onSelectTargetLanguage = { viewModel.selectTargetLanguage(it) }
            )
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                TextInput(
                    language = uiState.value.sourceLang.title,
                    text = uiState.value.inputText,
                    onTextChange = { viewModel.updateInputText(it) },
                    onClearText = { viewModel.clearInputText() },
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TranslateButton(
                onTranslate = { viewModel.translateText() },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            uiState.value.translatedText?.let {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    TranslationResult(
                        result = it,
                        modifier = Modifier.padding(16.dp),
                        addToFavourite = {
                            viewModel.addToFavorite()
                        }
                    )
                }
            }
        }
    }
}

