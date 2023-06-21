package com.flightsearch.ui.screens.search

import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite

data class SearchUiState(
    val searchQuery: String = "",
    val selectedCode: String = "",
    val airportList: List<Airport> = emptyList(),
    val favoriteList: List<Favorite> = emptyList()
)
