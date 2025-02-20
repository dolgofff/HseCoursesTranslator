package ru.hse.coursehse.app.screen.favourite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hse.coursehse.app.core.data.entity.Favourite
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel()
) {

    val favouriteList = viewModel.getFavourites().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(title = { Text("Favourite") })

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(favouriteList.value) { favourite ->
                FavouriteItem(favourite)
            }
        }
    }
}

@Composable
fun FavouriteItem(favourite: Favourite) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Source: ${favourite.sourceText}")
        Text(text = "Translation: ${favourite.sourceText}")
        Text(text = "Timestamp: ${SimpleDateFormat().format(favourite.timestamp)}")
    }
}