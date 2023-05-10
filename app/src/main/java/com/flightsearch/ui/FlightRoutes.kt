package com.flightsearch.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flightsearch.navigation.NavigationDestination

object FlightRoutes : NavigationDestination{
    override val route = "route"
    const val airportArgument = "airportDestinations"
    val routeWithArgs ="$route/{$airportArgument}"
}

@Composable
fun FlightRoutesScreen(
    modifier: Modifier = Modifier,
    airportIataCode: String
) {
        Text(text = airportIataCode)
}