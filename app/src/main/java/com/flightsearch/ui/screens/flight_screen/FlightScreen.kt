package com.flightsearch.ui.screens.flight_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flightsearch.navigation.NavigationDestination

object FlightScreenDestination: NavigationDestination{
    override val route: String = "flight_screen"
    const val codeArg = "code"
    val routeWithArgs = "$route/{$codeArg}"
}

@Composable
fun FlightScreen() {
    val viewModel: FlightViewModel = viewModel(factory = FlightViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState().value

    val context = LocalContext.current

    Column {
        FlightResults(
            departureAirport = uiState.departureAirport,
            destinationList = uiState.destinationList,
            favoriteList = uiState.favoriteList,
            onFavoriteClick = { s1: String, s2: String ->
                viewModel.addFavoriteFlight(s1, s2)
                if (viewModel.flightAdded){
                    Toast.makeText(context, "Removed Favorite", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Added Favorite", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}