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
    val uiState = viewModel.uiState.collectAsState().value

    Column(modifier = modifier) {
        SearchTextField(
            query = uiState.searchQuery,
            onQueryChange = {
                viewModel.updateQuery(it)
                viewModel.updateSelectedCode("")
                viewModel.onQueryChange(it)
            }
        )

        if(uiState.searchQuery.isEmpty()){

            val favoriteList = uiState.favoriteList
            val airportList = uiState.airportList

            if(favoriteList.isNotEmpty()){

            }else{
                Text(text= "No favorites yet")
            }
        } else {

            val airports = uiState.airportList

            SearchResults(
                airports = airports,
                onSelectCode = onSelectCode
            )
        }
    }
}