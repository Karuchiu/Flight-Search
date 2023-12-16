package com.flightsearch.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.flightsearch.models.Favorite
import com.flightsearch.navigation.NavigationDestination
import com.flightsearch.search.viewmodel.SearchViewModel

object SearchDestination: NavigationDestination {
    override val route = "home"
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onSelectCode: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val airports = uiState.airportList
    Log.d("Composable", "Rendering FlightResults with ${airports.size}")

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
                FavoriteResult(
                    airportList = airportList,
                    favoriteList = favoriteList,
                    onFavoriteClick = {
                        departureCode: String, destinationCode: String ->

                            val tmp = Favorite(
                                id = favoriteList.filter {
                                         (it.departureCode == departureCode && it.destinationCode == destinationCode)
                                }.first().id,
                                departureCode = departureCode,
                                destinationCode = destinationCode
                            )
                            viewModel.removeFavorite(tmp)

                    }
                )
            }else{
                Text(text= "No favorites yet")
            }
        } else {
            //val airports = uiState.airportList

            SearchResults(
                airports = airports,
                onSelectCode = onSelectCode
            )
        }
    }
}