package com.flightsearch.ui.screens.flight_screen

import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite

data class FlightUiState(
    val code: String = "",
    val favoriteList: List<Favorite> = emptyList(),
    val destinationList: List<Airport> = emptyList(),
    val departureAirport: Airport = Airport()
)