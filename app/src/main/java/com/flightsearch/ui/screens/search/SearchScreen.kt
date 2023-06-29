package com.flightsearch.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flightsearch.navigation.NavigationDestination

object SearchDestination: NavigationDestination {
    override val route = "home"
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onSelectCode: (String) -> Unit
) {
    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState()

    Column(modifier = modifier) {
        SearchTextField(
            query = uiState.value.searchQuery,
            onQueryChange = {
                viewModel.updateQuery(it)
                viewModel.updateSelectedCode("")
                viewModel.onQueryChange(it)
            }
        )

        if(uiState.value.searchQuery.isEmpty()){

            val favoriteList = uiState.value.favoriteList
            val airportList = uiState.value.airportList

            if(favoriteList.isNotEmpty()){

            }else{
                Text(text= "No favorites yet")
            }
        } else {

            val airports = uiState.value.airportList

            SearchResults(
                airports = airports,
                onSelectCode = onSelectCode
            )
        }
    }
}