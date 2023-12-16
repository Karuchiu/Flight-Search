package com.flightsearch.flight

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.flightsearch.flight.viewmodel.FlightViewModel
import com.flightsearch.navigation.NavigationDestination

object FlightScreenDestination: NavigationDestination{
    override val route: String = "flight_screen"
    const val codeArg = "code"
    val routeWithArgs = "$route/{$codeArg}"
}

@Composable
fun FlightScreen(
    viewModel: FlightViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column {
        FlightResults(
            departureAirport = uiState.departureAirport,
            destinationList = uiState.destinationList,
            viewModel = viewModel
        )
    }
}